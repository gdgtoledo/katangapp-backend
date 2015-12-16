package internal.business;

import business.ClosestPointsAlgorithm;
import business.Finder;
import business.http.HttpService;
import business.store.Store;

import internal.business.geolocation.KatangappAlgorithm;
import internal.business.http.UnautoHttpService;
import internal.business.parser.HTMLParser;
import internal.business.store.KatangappStore;

import models.BusStop;
import models.BusStopResult;
import models.Point;
import models.PolarSegment;
import models.QueryResult;
import models.ReferenceablePoint;
import models.RouteResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author mdelapenya
 */
public class BusStopsFinder implements Finder {

	public QueryResult findRoutes(
		double latitude, double longitude, int radius) {

		ReferenceablePoint currentLocation = new Point(latitude, longitude);

		Set<ReferenceablePoint> dataSet = new HashSet<ReferenceablePoint>(
			getStore().values());

		List<PolarSegment> polarSegments = getAlgorithm().closestSegments(
			currentLocation, dataSet, radius);

		List<BusStopResult> busStopResults = new ArrayList<>();

		if (polarSegments.isEmpty()) {
			return new QueryResult(busStopResults);
		}

		for (PolarSegment polarSegment : polarSegments) {
			ReferenceablePoint to = polarSegment.getTo();

			BusStop busStop = (BusStop)to;

			String responseHtml = getHttpService().get(
				busStop.getRouteId(), busStop.getId(), busStop.getOrder());

			List<RouteResult> routeResults = HTMLParser.parseResponse(
				busStop.getRouteId(), busStop.getId(), new Date(),
				responseHtml);

			Collections.sort(routeResults);

			BusStopResult busStopResult = new BusStopResult(
				polarSegment.getDistance(), busStop, routeResults);

			busStopResults.add(busStopResult);
		}

		return new QueryResult(busStopResults);
	}

	public ClosestPointsAlgorithm getAlgorithm() {
		return algorithm;
	}

	public Store getStore() {
		return katangappStore;
	}

	public HttpService getHttpService() {
		return httpService;
	}

	private static ClosestPointsAlgorithm algorithm = new KatangappAlgorithm();
	private static HttpService httpService = new UnautoHttpService();
	private static Store katangappStore = KatangappStore.getInstance();

}
