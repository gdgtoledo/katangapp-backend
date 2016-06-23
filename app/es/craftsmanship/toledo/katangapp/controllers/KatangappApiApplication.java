package es.craftsmanship.toledo.katangapp.controllers;

import com.fasterxml.jackson.databind.JsonNode;

import es.craftsmanship.toledo.katangapp.business.exception.APIElementNotFoundException;
import es.craftsmanship.toledo.katangapp.business.store.JsonStore;
import es.craftsmanship.toledo.katangapp.business.store.Store;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.Route;

import play.libs.Json;

import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class KatangappApiApplication extends BaseKatangaApplication {

    public Result busStop(String id) {
        try {
            BusStop busStop = store.getBusStop(id);

            return prettyPrint(Json.toJson(busStop));
        }
        catch (APIElementNotFoundException e) {
            return notFound(e.getApiError());
        }
    }

    public Result busStops() {
        JsonStore busStopsJsonStore = store.getBusStopsJsonStore();

        return prettyPrint(busStopsJsonStore.getJson());
    }

    public Result route(String id) {
        try {
            Route route = store.getRoute(id);

            return prettyPrint(Json.toJson(route));
        }
        catch (APIElementNotFoundException e){
            return notFound(e.getApiError());
        }
    }

    public Result routes() {
        JsonStore routesJsonStore = store.getRoutesJsonStore();

        JsonNode jsonNode = routesJsonStore.getJson();

        return prettyPrint(Json.toJson(jsonNode));
    }

    private static Store store = KatangappStore.getInstance();

}
