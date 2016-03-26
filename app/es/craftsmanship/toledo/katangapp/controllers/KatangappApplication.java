package es.craftsmanship.toledo.katangapp.controllers;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.inject.Inject;

import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.business.exception.APIException;
import es.craftsmanship.toledo.katangapp.internal.controllers.JsonPrettyPrinter;
import es.craftsmanship.toledo.katangapp.models.QueryResult;

import play.libs.Json;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class KatangappApplication extends Controller {

    @Inject
    public KatangappApplication(Finder busStopFinder) {
        this.busStopFinder = busStopFinder;
    }

    public Result main(String lt, String ln, int r) {
        double dLatitude = Double.parseDouble(lt);
        double dLongitude = Double.parseDouble(ln);

        try {
            QueryResult queryResult = busStopFinder.findRoutes(
                dLatitude, dLongitude, r);

            JsonNode node = Json.toJson(queryResult);

            PrettyPrinter prettyPrinter = new JsonPrettyPrinter(
                request(), node);

            return prettyPrinter.prettyPrint();
        }
        catch (InterruptedException ie) {
            APIException apiException = new APIException(ie.getMessage());

            return notFound(apiException.getApiError());
        }
            
    }

    private Finder busStopFinder;

}