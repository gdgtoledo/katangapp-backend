package es.craftsmanship.toledo.katangapp.controllers;

import com.fasterxml.jackson.databind.JsonNode;

import es.craftsmanship.toledo.katangapp.business.exception.APIElementNotFoundException;
import es.craftsmanship.toledo.katangapp.business.store.JsonStore;
import es.craftsmanship.toledo.katangapp.business.store.Store;
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

            return prettyPrinter.prettyPrintWhenNeeded();
        }
        catch (APIElementNotFoundException e) {
            return notFound(e.getApiError());
        }
    }

    public Result busStops() {
        JsonNode jsonNode = busStops.getJson();

        JsonPrettyPrinter prettyPrinter = new JsonPrettyPrinter(
            request(), jsonNode);

        return prettyPrinter.prettyPrintWhenNeeded();
    }

    public Result route(String id) {
        try {
            Route route = store.getRoute(id);

            JsonNode jsonNode = Json.toJson(route);

            JsonPrettyPrinter prettyPrinter = new JsonPrettyPrinter(
                request(), jsonNode);

            return prettyPrinter.prettyPrintWhenNeeded();
        }
        catch (APIElementNotFoundException e){
            return notFound(e.getApiError());
        }
    }

    public Result routes() {
        JsonNode jsonNode = routes.getJson();

        JsonPrettyPrinter prettyPrinter = new JsonPrettyPrinter(
            request(), jsonNode);

        return prettyPrinter.prettyPrintWhenNeeded();
    }

    private static Store store = KatangappStore.getInstance();

    private static JsonStore busStops = store.getBusStopsJsonStore();
    private static JsonStore routes = store.getRoutesJsonStore();

}
