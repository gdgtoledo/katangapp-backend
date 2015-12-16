package business.store;

import models.BusStop;
import models.Route;

import java.util.Map;

/**
 * @author mdelapenya
 */
public interface Store extends Map<String, BusStop> {

	BusStop getBusStop(String id);

	Map<String, BusStop> getStore();

	Route getRoute(String id);

}
