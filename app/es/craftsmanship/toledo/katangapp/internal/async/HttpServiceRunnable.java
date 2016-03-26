package es.craftsmanship.toledo.katangapp.internal.async;

import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.business.parser.Parser;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.Constants;
import es.craftsmanship.toledo.katangapp.models.ReferenceablePoint;
import es.craftsmanship.toledo.katangapp.models.RouteResult;
import es.craftsmanship.toledo.katangapp.models.Segment;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class HttpServiceRunnable implements Runnable {

	public HttpServiceRunnable(
		List<BusStopResult> busStopResults, HttpService httpService,
		Parser parser, Segment segment) {

		_busStopResults = busStopResults;
		_httpService = httpService;
		_parser = parser;
		_segment = segment;
	}

	@Override
	public void run() {
		ReferenceablePoint to = _segment.getTo();

		BusStop busStop = (BusStop) to;

		String responseHtml = _httpService.get(
			busStop.getRouteId(), busStop.getId(), busStop.getOrder());

		Calendar calendar = Calendar.getInstance();

		calendar.setTimeZone(Constants.TZ_TOLEDO);

		List<RouteResult> routeResults = _parser.parseResponse(
			busStop.getRouteId(), calendar.getTime(), responseHtml);

		Collections.sort(routeResults);

		BusStopResult busStopResult = new BusStopResult(
			_segment.getDistance(), busStop, routeResults);

		_busStopResults.add(busStopResult);
	}

	private List<BusStopResult> _busStopResults;
	private HttpService _httpService;
	private Parser _parser;
	private Segment _segment;

}
