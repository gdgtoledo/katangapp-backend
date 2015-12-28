package controllers;

import business.exception.APIElementNotFoundException;
import business.store.JsonStore;
import business.store.Store;

import internal.business.store.KatangappStore;

import models.BusStop;
import models.Route;

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

            return ok(Json.toJson(busStop));
        }
        catch (APIElementNotFoundException e) {
            return notFound(e.getApiError());
        }
    }

    public Result busStops() {
        return ok(busStops.getJson());
    }

    public Result route(String id) {
        try {
            Route route = store.getRoute(id);

            return ok(Json.toJson(route));
        }
        catch (APIElementNotFoundException e){
            return notFound(e.getApiError());
        }
    }

    public Result routes() {
        return ok(routes.getJson());
    }

    private static Store store = KatangappStore.getInstance();

    private static JsonStore busStops = store.getBusStopsJsonStore();
    private static JsonStore routes = store.getRoutesJsonStore();

}
