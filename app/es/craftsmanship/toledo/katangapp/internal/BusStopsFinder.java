package es.craftsmanship.toledo.katangapp.internal;

import com.google.inject.Inject;

import es.craftsmanship.toledo.katangapp.business.ClosestPointsAlgorithm;
import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.business.parser.Parser;
import es.craftsmanship.toledo.katangapp.business.store.Store;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.Constants;
import es.craftsmanship.toledo.katangapp.models.Point;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.models.ReferenceablePoint;
import es.craftsmanship.toledo.katangapp.models.RouteResult;
import es.craftsmanship.toledo.katangapp.models.Segment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.libs.F.Function;
import play.libs.F.Function0;
import play.libs.F.Promise;

/**
 * @author mdelapenya
 */
public class BusStopsFinder implements Finder {

	@Inject
	public BusStopsFinder(
		ClosestPointsAlgorithm closestPointsAlgorithm, Parser parser,
		HttpService httpService) {

		this.algorithm = closestPointsAlgorithm;
		this.httpService = httpService;
		this.parser = parser;
	}

	public Promise<QueryResult> findRoutes(
			double latitude, double longitude, int radius)
		throws InterruptedException {

		ReferenceablePoint currentLocation = new Point(latitude, longitude);

		Map<String, BusStop> busStopMap = katangappStore.getBusStopStore();

		Set<ReferenceablePoint> dataSet = new HashSet<ReferenceablePoint>(
			busStopMap.values());

		List<Segment> segments = algorithm.closestSegments(
			currentLocation, dataSet, radius);

		if (segments.isEmpty()) {
			Promise<QueryResult> queryResultPromise = Promise.promise(
				new Function0<QueryResult>() {

					@Override
					public QueryResult apply() throws Throwable {
						return new QueryResult(
							Collections.<BusStopResult>emptyList());
					}

				});

			return queryResultPromise;
		}

		List<Promise<BusStopResult>> busStopResultPromises =
			new ArrayList<>();

		for (final Segment segment : segments) {
			ReferenceablePoint to = segment.getTo();

			final BusStop busStop = (BusStop) to;

			Promise<String> responseHtml = httpService.get(
				busStop.getRouteId(), busStop.getId(), busStop.getOrder());

			Calendar calendar = Calendar.getInstance();

			calendar.setTimeZone(Constants.TZ_TOLEDO);

			Promise<List<RouteResult>> routesPromise =
				parser.parseResponse(
					busStop.getRouteId(), calendar.getTime(), responseHtml);

			Promise<BusStopResult> busStopResultPromise = routesPromise.map(
				new Function<List<RouteResult>, BusStopResult>() {

					@Override
					public BusStopResult apply(List<RouteResult> routeResults)
						throws Throwable {

						Collections.sort(routeResults);

						BusStopResult busStopResult = new BusStopResult(
							segment.getDistance(), busStop, routeResults);

						return busStopResult;
					}

				});

			busStopResultPromises.add(busStopResultPromise);
		}

		Promise<List<BusStopResult>> sequence = Promise.sequence(
			busStopResultPromises);

		Promise<QueryResult> queryResultPromise = sequence.flatMap(
			new Function<List<BusStopResult>, Promise<QueryResult>>() {

			@Override
			public Promise<QueryResult> apply(
				final List<BusStopResult> busStopResults) throws Throwable {

				return Promise.promise(new Function0<QueryResult>() {

					@Override
					public QueryResult apply() throws Throwable {
						return new QueryResult(busStopResults);
					}

				});
			}
		});

		return queryResultPromise;
	}

	private static Store katangappStore = KatangappStore.getInstance();

	private ClosestPointsAlgorithm algorithm;
	private HttpService httpService;
	private Parser parser;

}