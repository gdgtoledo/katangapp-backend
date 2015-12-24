package internal.business.store;

import business.exception.NotFoundException;
import business.store.JsonStore;
import business.store.Store;

import models.BusStop;
import models.Route;
import models.json.BusStopDeserializer;

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

	@Override
	public BusStop getBusStop(String key) throws NotFoundException {
		Map<String, BusStop> busStopStore = getInstance().getBusStopStore();

		BusStop busStop = busStopStore.get(key);

		if (busStop == null) {
			throw new NotFoundException();
		}

		return busStop;
	}

	@Override
	public Map<String, BusStop> getBusStopStore() {
		return busStopStore;
	}

	@Override
	public Route getRoute(String key) throws NotFoundException {
		Map<String, Route> routeStore = getInstance().getRouteStore();

		Route route = routeStore.get(key);

		if (route == null) {
			throw new NotFoundException();
		}

		return route;
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
		JsonStore busStopsJsonStore = new BusStopsJsonStore();

		final JsonNode busStops = busStopsJsonStore.getJson();

		for (JsonNode busStopsArray : busStops) {
			ObjectMapper mapper = new ObjectMapper();

			SimpleModule module = new SimpleModule();

			module.addDeserializer(BusStop.class, new BusStopDeserializer());

			mapper.registerModule(module);

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
		JsonStore routesJsonStore = new RoutesJsonStore();

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

			storedBusStop.setRouteId(route.getId());
			storedBusStop.setOrder(busStop.getOrder());

			purgedBusStops.add(storedBusStop);
		}

		route.setBusStops(purgedBusStops);
	}

	private static Map<String, BusStop> busStopStore =
		new ConcurrentHashMap<>();
	private static Map<String, Route> routeStore = new ConcurrentHashMap<>();

	private static Store instance = new KatangappStore();

}
