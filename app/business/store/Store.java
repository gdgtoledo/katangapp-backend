package business.store;

import models.BusStop;
import models.Route;

import java.util.Map;

/**
 * @author mdelapenya
 */
public interface Store {

	BusStop getBusStop(String id);

	Map<String, BusStop> getBusStopStore();

	Route getRoute(String id);

	Map<String, Route> getRouteStore();

}
