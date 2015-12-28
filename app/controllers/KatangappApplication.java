package controllers;

import business.Finder;
import business.http.HttpService;

import internal.business.BusStopsFinder;
import internal.business.http.UnautoHttpService;

import models.QueryResult;

import com.fasterxml.jackson.databind.JsonNode;

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

    public void setHttpService(HttpService service) {
        httpClient = service;
    }

    public Result unauto(String idl, String idp, String ido) {
        String response = httpClient.get(idl, idp, ido);

        return ok(response);
    }

    private static Finder busStopFinder = new BusStopsFinder();
    private static HttpService httpClient = new UnautoHttpService();

}
