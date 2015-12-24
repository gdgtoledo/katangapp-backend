package business.store;

import business.exception.NotFoundException;

import models.BusStop;
import models.Route;

import java.util.Map;

/**
 * @author mdelapenya
 */
public interface Store {

	BusStop getBusStop(String id) throws NotFoundException;

	Map<String, BusStop> getBusStopStore();

	Route getRoute(String id) throws NotFoundException;

	Map<String, Route> getRouteStore();

}
