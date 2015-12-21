package controllers;

import business.store.JsonStore;
import business.store.Store;

import internal.business.store.BusStopsJsonStore;
import internal.business.store.KatangappStore;
import internal.business.store.RoutesJsonStore;

import models.BusStop;
import models.Route;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class KatangappApiApplication extends Controller {

    public static Result busStop(String id) {
        BusStop busStop = store.getBusStop(id);

        if (busStop != null) {
            return ok(Json.toJson(busStop));
        }

        return notFound(NOT_FOUND_MESSAGE);
    }

    public static Result busStops() {
        return ok(busStops.getJson());
    }

    public static Result route(String id) {
        Route route = store.getRoute(id);

        if (route != null) {
            return ok(Json.toJson(route));
        }

        return notFound(NOT_FOUND_MESSAGE);
    }

    public static Result routes() {
        return ok(routes.getJson());
    }

    private static final JsonNode NOT_FOUND_MESSAGE =
        Json.newObject().set("message", new TextNode("Not Found"));

    private static JsonStore busStops = new BusStopsJsonStore();
    private static JsonStore routes = new RoutesJsonStore();
    private static Store store = KatangappStore.getInstance();

}
