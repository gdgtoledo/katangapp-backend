package es.craftsmanship.toledo.katangapp.controllers;

import com.fasterxml.jackson.databind.JsonNode;

import es.craftsmanship.toledo.katangapp.business.exception.APIElementNotFoundException;
import es.craftsmanship.toledo.katangapp.business.store.JsonStore;
import es.craftsmanship.toledo.katangapp.business.store.Store;
import es.craftsmanship.toledo.katangapp.internal.controllers.JsonPrettyPrinter;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.Route;

import play.libs.Json;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class KatangappApiApplication extends Controller {

    public Result busStop(String id) {
        try {
            BusStop busStop = store.getBusStop(id);

            JsonNode jsonNode = Json.toJson(busStop);

            JsonPrettyPrinter prettyPrinter = new JsonPrettyPrinter(
                request(), jsonNode);

            return prettyPrinter.prettyPrint();
        }
        catch (APIElementNotFoundException e) {
            return notFound(e.getApiError());
        }
    }

    public Result busStops() {
        JsonStore busStopsJsonStore = store.getBusStopsJsonStore();

        JsonNode jsonNode = busStopsJsonStore.getJson();

        JsonPrettyPrinter prettyPrinter = new JsonPrettyPrinter(
            request(), jsonNode);

        return prettyPrinter.prettyPrint();
    }

    public Result route(String id) {
        try {
            Route route = store.getRoute(id);

            JsonNode jsonNode = Json.toJson(route);

            JsonPrettyPrinter prettyPrinter = new JsonPrettyPrinter(
                request(), jsonNode);

            return prettyPrinter.prettyPrint();
        }
        catch (APIElementNotFoundException e){
            return notFound(e.getApiError());
        }
    }

    public Result routes() {
        JsonStore routesJsonStore = store.getRoutesJsonStore();

        JsonNode jsonNode = routesJsonStore.getJson();

        JsonPrettyPrinter prettyPrinter = new JsonPrettyPrinter(
            request(), jsonNode);

        return prettyPrinter.prettyPrint();
    }

    private static Store store = KatangappStore.getInstance();

}
