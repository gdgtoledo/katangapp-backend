package internal.business;

import business.ClosestPointsAlgorithm;
import business.Finder;
import business.http.HttpService;
import business.parser.Parser;
import business.store.Store;

import internal.business.geolocation.KatangappAlgorithm;
import internal.business.http.UnautoHttpService;
import internal.business.parser.HTMLParser;
import internal.business.store.KatangappStore;

import models.BusStop;
import models.BusStopResult;
import models.Constants;
import models.Point;
import models.PolarSegment;
import models.QueryResult;
import models.ReferenceablePoint;
import models.RouteResult;

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

	public QueryResult findRoutes(
		double latitude, double longitude, int radius) {

		ReferenceablePoint currentLocation = new Point(latitude, longitude);

		Map<String, BusStop> busStopMap = getStore().getBusStopStore();

		Set<ReferenceablePoint> dataSet = new HashSet<ReferenceablePoint>(
			busStopMap.values());

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

			Calendar calendar = Calendar.getInstance();

			calendar.setTimeZone(Constants.TZ_TOLEDO);

			List<RouteResult> routeResults = getParser().parseResponse(
				busStop.getRouteId(), calendar.getTime(), responseHtml);

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

	public HttpService getHttpService() {
		return httpService;
	}

	public Parser getParser() {
		return parser;
	}

	public Store getStore() {
		return katangappStore;
	}

	private static ClosestPointsAlgorithm algorithm = new KatangappAlgorithm();
	private static HttpService httpService = new UnautoHttpService();
	private static Parser parser = new HTMLParser();
	private static Store katangappStore = KatangappStore.getInstance();

}
