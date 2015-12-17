package internal.business.parser;

import business.parser.Parser;

import models.RouteResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * @author mdelapenya
 */
public class HTMLParser implements Parser {

	public List<RouteResult> parseResponse(
		String routeId, Date queryDate, String html) {

		List<RouteResult> results = new ArrayList<>();

		Document doc = Jsoup.parse(html);

		Element hour = doc.getElementById("hora");

		Matcher matcher = captureGroup(hour.text(), REGEXP_HOUR);

		String arrivalTime = matcher.group(1);

		RouteResult mainRouteResult = parseRouteResult(
			queryDate, routeId, arrivalTime);

		results.add(mainRouteResult);

		Elements connectionsUl = doc.getElementsByTag("ul");

		for (Element connectionUl : connectionsUl) {
			Elements connectionsLi = connectionUl.getElementsByTag("li");

			for (Element connectionLi : connectionsLi) {
				matcher = captureGroup(connectionLi.text(), REGEXP_CONNECTIONS);

				String connectionRouteId = matcher.group(1);
				String connectionArrivalTime = matcher.group(2);

				RouteResult connectionRouteResult = parseRouteResult(
					queryDate, connectionRouteId, connectionArrivalTime);

				results.add(connectionRouteResult);
			}
		}

		return results;
	}

	private static Matcher captureGroup(String text, String regexp) {
		Pattern pattern = Pattern.compile(regexp);

		Matcher matcher = pattern.matcher(text);

		matcher.matches();

		return matcher;
	}

	private static RouteResult parseRouteResult(
		Date queryDate, String routeId, String arrivalTime) {

		int colonIndexOf = arrivalTime.indexOf(":");

		String strConnectionHour = arrivalTime.substring(
			0, colonIndexOf);

		int connectionHour = Integer.parseInt(strConnectionHour);

		String strConnectionMinutes = arrivalTime.substring(
			colonIndexOf + 1, arrivalTime.length());

		int connectionMinutes = Integer.parseInt(strConnectionMinutes);

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(queryDate);

		calendar.set(Calendar.HOUR_OF_DAY, connectionHour);
		calendar.set(Calendar.MINUTE, connectionMinutes);

		Date to = calendar.getTime();

		int minutesLeft = minutesLeft(queryDate, to);

		if (minutesLeft < 0) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);

			to = calendar.getTime();

			minutesLeft = minutesLeft(queryDate, to);
		}

		return new RouteResult(routeId, minutesLeft);
	}

	private static int minutesLeft(Date from, Date to) {
		long diff = to.getTime() - from.getTime();

		long minutesLeft = diff / (1000 * 60);

		return (int) minutesLeft;
	}

	private static final String REGEXP_HOUR =
		".*:\\s(\\d{1,2}:\\d{2})\\s.*:\\s(.*)";

	private static final String REGEXP_CONNECTIONS =
		".*\\((.*)\\):\\s(\\d{1,2}:\\d{2})";

}
