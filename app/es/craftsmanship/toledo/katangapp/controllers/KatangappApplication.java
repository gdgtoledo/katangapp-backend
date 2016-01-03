package es.craftsmanship.toledo.katangapp.controllers;

import com.fasterxml.jackson.databind.JsonNode;

import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.internal.BusStopsFinder;
import es.craftsmanship.toledo.katangapp.internal.http.UnautoHttpService;
import es.craftsmanship.toledo.katangapp.models.QueryResult;

import play.libs.Json;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class KatangappApplication extends Controller {

    public Result main(String lt, String ln, int r) {
        double dLatitude = Double.parseDouble(lt);
        double dLongitude = Double.parseDouble(ln);

        QueryResult queryResult = busStopFinder.findRoutes(
            dLatitude, dLongitude, r);

        JsonNode node = Json.toJson(queryResult);

        JsonPrettyPrinter prettyPrinter = new JsonPrettyPrinter(
            request(), node);

        return prettyPrinter.prettyPrintWhenNeeded();
    }

    public void setBusStopFinder(Finder finder) {
        busStopFinder = finder;
    }

    public Result unauto(String idl, String idp, String ido) {
        HttpService httpService = busStopFinder.getHttpService();

        String response = httpService.get(idl, idp, ido);

        return ok(response);
    }

    private static Finder busStopFinder = new BusStopsFinder(
        new UnautoHttpService());

}
