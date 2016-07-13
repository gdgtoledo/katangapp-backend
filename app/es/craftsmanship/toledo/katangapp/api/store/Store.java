package es.craftsmanship.toledo.katangapp.api.store;

import es.craftsmanship.toledo.katangapp.api.exception.APIElementNotFoundException;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.Route;

import java.util.Map;

/**
 * @author mdelapenya
 */
public interface Store {

	/**
	 * This method retrieves a bus stop identified by the <code>id</code> param
	 * from the store.
	 *
	 * @param id the identifier of the bus stop
	 *
	 * @return the bus stop identified by the id
	 *
	 * @throws APIElementNotFoundException when no bus stop exists in the store
	 *                           with provided id
	 */
	BusStop getBusStop(String id) throws APIElementNotFoundException;

	Map<String, BusStop> getBusStopStore();

	/**
	 * This method retrieves a route identified by the <code>id</code> param
	 * from the store.
	 *
	 * @param id the identifier of the route
	 *
	 * @return the route identified by the id
	 *
	 * @throws APIElementNotFoundException when no route exists in the store
	 *                           with provided id
	 */
	Route getRoute(String id) throws APIElementNotFoundException;

	Map<String, Route> getRouteStore();

}
