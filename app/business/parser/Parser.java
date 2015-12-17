package business.parser;

import models.RouteResult;

import java.util.Date;
import java.util.List;

/**
 * @author mdelapenya
 */
public interface Parser {

	List<RouteResult> parseResponse(
		String routeId, Date queryDate, String html);

}
