package es.craftsmanship.toledo.katangapp.api.controllers;

import es.craftsmanship.toledo.katangapp.api.exception.APIElementNotFoundException;
import es.craftsmanship.toledo.katangapp.api.store.Store;
import es.craftsmanship.toledo.katangapp.internal.controllers.BaseKatangaApplication;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.Route;

import java.util.Collection;
import java.util.Map;

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
        Map<String, BusStop> busStops = store.getBusStopStore();

        Collection<BusStop> values = busStops.values();

        return prettyPrint(Json.toJson(values.toArray()));
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
        Map<String, Route> routes = store.getRouteStore();

        Collection<Route> values = routes.values();

        return prettyPrint(Json.toJson(values.toArray()));
    }

    private static Store store = KatangappStore.getInstance();

}
