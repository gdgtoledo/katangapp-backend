package es.craftsmanship.toledo.katangapp.internal;

import com.google.inject.Inject;

import es.craftsmanship.toledo.katangapp.business.ClosestPointsAlgorithm;
import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.business.parser.Parser;
import es.craftsmanship.toledo.katangapp.business.store.Store;
import es.craftsmanship.toledo.katangapp.internal.async.HttpServiceRunnable;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.Point;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.models.ReferenceablePoint;
import es.craftsmanship.toledo.katangapp.models.Segment;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

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
			double latitude, double longitude, int radius)
		throws InterruptedException {

		ReferenceablePoint currentLocation = new Point(latitude, longitude);

		Map<String, BusStop> busStopMap = katangappStore.getBusStopStore();

		Set<ReferenceablePoint> dataSet = new HashSet<ReferenceablePoint>(
			busStopMap.values());

		List<Segment> segments = algorithm.closestSegments(
			currentLocation, dataSet, radius);

		List<BusStopResult> busStopResults = new CopyOnWriteArrayList<>();

		if (segments.isEmpty()) {
			return new QueryResult(busStopResults);
		}

		for (Segment segment : segments) {
			Thread httpThread = new Thread(
				new HttpServiceRunnable(
					busStopResults, httpService, parser, segment));

			httpThread.start();

			httpThread.join();
		}

		return new QueryResult(busStopResults);
	}

	private static Store katangappStore = KatangappStore.getInstance();

	private ClosestPointsAlgorithm algorithm;
	private HttpService httpService;
	private Parser parser;

}