package es.craftsmanship.toledo.katangapp.internal.store;

import es.craftsmanship.toledo.katangapp.api.exception.APIElementNotFoundException;
import es.craftsmanship.toledo.katangapp.api.store.JsonStore;
import es.craftsmanship.toledo.katangapp.api.store.Store;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.Route;
import es.craftsmanship.toledo.katangapp.models.json.BusStopDeserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mdelapenya
 */
public final class KatangappStore implements Store {

	public static Store getInstance() {
		return instance;
	}

	/**
	 * This method retrieves a bus stop identified by the <code>key</code> param
	 * from the store.
	 *
	 * @param key the identifier of the bus stop
	 *
	 * @return the bus stop identified by the key
	 *
	 * @throws APIElementNotFoundException when no bus stop exists in the store
	 *                           with provided key
	 */
	@Override
	public BusStop getBusStop(String key) throws APIElementNotFoundException {
		Map<String, BusStop> busStopStore = getInstance().getBusStopStore();

		BusStop busStop = busStopStore.get(key);

		if (busStop == null) {
			throw new APIElementNotFoundException();
		}

		return busStop;
	}

	public JsonStore getBusStopsJsonStore() {
		return busStopsJsonStore;
	}

	@Override
	public Map<String, BusStop> getBusStopStore() {
		return busStopStore;
	}

	/**
	 * This method retrieves a route identified by the <code>key</code> param
	 * from the store.
	 *
	 * @param key the identifier of the route
	 *
	 * @return the route identified by the key
	 *
	 * @throws APIElementNotFoundException when no route exists in the store
	 *                           with provided key
	 */
	@Override
	public Route getRoute(String key) throws APIElementNotFoundException {
		Map<String, Route> routeStore = getInstance().getRouteStore();

		Route route = routeStore.get(key);

		if (route == null) {
			throw new APIElementNotFoundException();
		}

		return route;
	}

	public JsonStore getRoutesJsonStore() {
		return routesJsonStore;
	}

	@Override
	public Map<String, Route> getRouteStore() {
		return routeStore;
	}

	private KatangappStore() {
		populateBusStops();
		populateRoutes();
	}

	private void populateBusStops() {
		final JsonNode busStops = busStopsJsonStore.getJson();

		ObjectMapper mapper = new ObjectMapper();

		SimpleModule module = new SimpleModule();

		module.addDeserializer(BusStop.class, new BusStopDeserializer());

		mapper.registerModule(module);

		for (JsonNode busStopsArray : busStops) {
			try {
				BusStop[] busStopModels = mapper.treeToValue(
					busStopsArray, BusStop[].class);

				for (BusStop busStop : busStopModels) {
					busStopStore.put(busStop.getId(), busStop);
				}
			}
			catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	private void populateRoutes() {
		final JsonNode routes = routesJsonStore.getJson();

		for (JsonNode routesArray : routes) {
			ObjectMapper mapper = new ObjectMapper();

			try {
				Route[] routeModels = mapper.treeToValue(
					routesArray, Route[].class);

				for (Route route : routeModels) {
					purgeBusStopsWithoutCoordinates(route);

					routeStore.put(route.getId(), route);
				}
			}
			catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	private void purgeBusStopsWithoutCoordinates(Route route) {
		List<BusStop> busStops = route.getBusStops();

		List<BusStop> purgedBusStops = new ArrayList<>();

		for (BusStop busStop : busStops) {
			BusStop storedBusStop = busStopStore.get(
				busStop.getId());

			if (storedBusStop == null) {
				continue;
			}

			BusStop routeBusStop = new BusStop(
				route.getId(), storedBusStop.getId(), busStop.getOrder(),
				storedBusStop.getLatitude(), storedBusStop.getLongitude(),
				storedBusStop.getAddress());

			purgedBusStops.add(routeBusStop);
		}

		route.setBusStops(purgedBusStops);
	}

	private static Map<String, BusStop> busStopStore =
		new ConcurrentHashMap<>();
	private static Map<String, Route> routeStore = new ConcurrentHashMap<>();

	private static JsonStore busStopsJsonStore = new BusStopsJsonStore();
	private static JsonStore routesJsonStore = new RoutesJsonStore();

	private static Store instance = new KatangappStore();
}
