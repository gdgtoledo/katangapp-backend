package business;

import business.geolocation.GeoLocator;

import business.store.BusStopStore;

import models.BusStop;
import models.BusStopResult;
import models.Point;
import models.QueryResult;
import models.ReferenceablePoint;
import models.RouteResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author mdelapenya
 */
public class BusStopsFinder implements Finder{

	public QueryResult findRoutes(
		double latitude, double longitude, int radius) {

		ReferenceablePoint currentLocation = new Point(latitude, longitude);

		BusStopStore busStopStore = BusStopStore.getInstance();

		Collection<BusStop> busStops = busStopStore.values();

		Set<ReferenceablePoint> dataSet = new HashSet<ReferenceablePoint>(
			busStops);

		List<ReferenceablePoint> closestPoints = GeoLocator.closestPoints(
			currentLocation, dataSet, radius);

		List<BusStopResult> busStopResults = new ArrayList<>();

		if (closestPoints.isEmpty()) {
			return new QueryResult(busStopResults);
		}

		for (ReferenceablePoint closestPoint : closestPoints) {
			BusStop busStop = (BusStop)closestPoint;

			List<RouteResult> routeResults = new ArrayList<>();

			for (int j = 1; j < 5; j++) {
				String routeId = "L" + j;

				RouteResult routeResult = new RouteResult(routeId, 1000*j);

				routeResults.add(routeResult);
			}

			BusStopResult busStopResult = new BusStopResult(
				busStop, routeResults);

			busStopResults.add(busStopResult);
		}

		return new QueryResult(busStopResults);
	}

}