/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
