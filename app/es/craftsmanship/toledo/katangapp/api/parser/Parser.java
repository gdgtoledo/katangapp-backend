package es.craftsmanship.toledo.katangapp.api.parser;

import es.craftsmanship.toledo.katangapp.models.RouteResult;

import java.util.Date;
import java.util.List;

import play.libs.F.Promise;

/**
 * @author mdelapenya
 */
public interface Parser {

	Promise<List<RouteResult>> parseResponse(
		String routeId, Date queryDate, Promise<String> html);

}
