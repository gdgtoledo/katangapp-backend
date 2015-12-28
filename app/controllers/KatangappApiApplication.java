package controllers;

import business.exception.APIElementNotFoundException;
import business.store.JsonStore;
import business.store.Store;

import internal.business.store.KatangappStore;

import models.BusStop;
import models.Route;

import com.fasterxml.jackson.databind.JsonNode;

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
