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
import es.craftsmanship.toledo.katangapp.models.PolarSegment;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.models.ReferenceablePoint;
import es.craftsmanship.toledo.katangapp.models.RouteResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	public QueryResult findRoutes(
		double latitude, double longitude, int radius) {

		ReferenceablePoint currentLocation = new Point(latitude, longitude);

		Map<String, BusStop> busStopMap = katangappStore.getBusStopStore();

		Set<ReferenceablePoint> dataSet = new HashSet<ReferenceablePoint>(
			busStopMap.values());

		List<PolarSegment> polarSegments = algorithm.closestSegments(
			currentLocation, dataSet, radius);

		List<BusStopResult> busStopResults = new ArrayList<>();

		if (polarSegments.isEmpty()) {
			return new QueryResult(busStopResults);
		}

		for (PolarSegment polarSegment : polarSegments) {
			ReferenceablePoint to = polarSegment.getTo();

			BusStop busStop = (BusStop)to;

			String responseHtml = httpService.get(
				busStop.getRouteId(), busStop.getId(), busStop.getOrder());

			Calendar calendar = Calendar.getInstance();

			calendar.setTimeZone(Constants.TZ_TOLEDO);

			List<RouteResult> routeResults = parser.parseResponse(
				busStop.getRouteId(), calendar.getTime(), responseHtml);

			Collections.sort(routeResults);

			BusStopResult busStopResult = new BusStopResult(
				polarSegment.getDistance(), busStop, routeResults);

			busStopResults.add(busStopResult);
		}

		return new QueryResult(busStopResults);
	}

	public HttpService getHttpService() {
		return httpService;
	}

	private static Store katangappStore = KatangappStore.getInstance();

	private ClosestPointsAlgorithm algorithm;
	private HttpService httpService;
	private Parser parser;

}
