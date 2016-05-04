package es.craftsmanship.toledo.katangapp.controllers;

import es.craftsmanship.toledo.katangapp.business.store.Store;
import es.craftsmanship.toledo.katangapp.internal.controllers.JsonPrettyPrinter;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.models.BusStop;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.F;
import play.libs.Json;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author manudevelopia
 */
public class KatangappFavoriteApplication extends Controller {

    public F.Promise<Result> favorite(final String busStopId) {
        return F.Promise.promise(
            new F.Function0<Result>() {
                @Override
                public Result apply() throws Throwable {
                    BusStop busStop = store.getBusStop(busStopId);

                    JsonNode jsonNode = Json.toJson(busStop);

                    PrettyPrinter prettyPrinter = new JsonPrettyPrinter(
                        request(), jsonNode);

                    return prettyPrinter.prettyPrint();
                }
            }
        );

    }

    private Store store = KatangappStore.getInstance();

}
