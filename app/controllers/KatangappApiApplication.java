package controllers;

import business.Finder;
import business.store.JsonStore;
import business.store.Store;

import internal.business.BusStopsFinder;
import internal.business.store.BusStopsJsonStore;
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
        Store store = busStopFinder.getStore();

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
        Store store = busStopFinder.getStore();

        Route route = store.getRoute(id);

        if (route != null) {
            return ok(Json.toJson(route));
        }

        return notFound(NOT_FOUND_MESSAGE);
    }

    public static Result routes() {
        return ok(routes.getJson());
    }

    public static void setBusStopFinder(Finder finder) {
        busStopFinder = finder;
    }

    private static final JsonNode NOT_FOUND_MESSAGE =
        Json.newObject().set("message", new TextNode("Not Found"));

    private static JsonStore busStops = new BusStopsJsonStore();
    private static Finder busStopFinder = new BusStopsFinder();
    private static JsonStore routes = new RoutesJsonStore();

}
