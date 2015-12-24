package business.store;

import business.exception.APIException;

import models.BusStop;
import models.Route;

import java.util.Map;

/**
 * @author mdelapenya
 */
public interface Store {

	BusStop getBusStop(String id) throws APIException;

	Map<String, BusStop> getBusStopStore();

	Route getRoute(String id) throws APIException;

	Map<String, Route> getRouteStore();

}
