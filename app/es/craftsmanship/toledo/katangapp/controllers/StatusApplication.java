package es.craftsmanship.toledo.katangapp.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.internal.services.UnautoStatusService;
import es.craftsmanship.toledo.katangapp.services.StatusCheckService;
import es.craftsmanship.toledo.katangapp.services.StatusCheckServiceDiscoveryManager;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.common.base.Stopwatch;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.Json;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class StatusApplication extends Controller {

    @Inject
    public StatusApplication(HttpService httpService) {
        this.httpService = httpService;
        this.serviceDiscoveryManager = new KatangaServiceDiscoveryManager();
    }

    public Promise<Result> status() {
        List<StatusCheckService> services =
            serviceDiscoveryManager.getServices();

        List<Promise<JsonNode>> resultsPromiseList = new ArrayList<>();

        for (StatusCheckService service : services) {
            resultsPromiseList.add(observeEllapsedTime(service));
        }

        Promise<List<JsonNode>> sequence = Promise.sequence(resultsPromiseList);

        return sequence.map(new Function<List<JsonNode>, Result>() {

            @Override
            public Result apply(List<JsonNode> jsonNodes) throws Throwable {
                return ok(Json.toJson(jsonNodes));
            }

        });
    }

    private Promise<JsonNode> observeEllapsedTime(StatusCheckService service) {
        final Stopwatch stopwatch = Stopwatch.createStarted();

        Promise<JsonNode> healthCheckPromise = service.healthCheck();

        return healthCheckPromise.map(new Function<JsonNode, JsonNode>() {

                @Override
                public JsonNode apply(JsonNode jsonNode) throws Throwable {
                    stopwatch.stop();

                    ObjectNode objectNode = Json.newObject();

                    objectNode.put("service", jsonNode);

                    ObjectNode elapsedNode = Json.newObject();

                    elapsedNode.put(
                        "time", stopwatch.elapsed(TimeUnit.MILLISECONDS));
                    elapsedNode.put("units", "milliseconds");

                    objectNode.put("elapsed", elapsedNode);

                    return objectNode;
                }

            });
    }

    private final HttpService httpService;
    private final StatusCheckServiceDiscoveryManager serviceDiscoveryManager;

    private class KatangaServiceDiscoveryManager
        implements StatusCheckServiceDiscoveryManager {

        public KatangaServiceDiscoveryManager() {
            statusCheckServices = new ArrayList<>();

            statusCheckServices.add(
                new UnautoStatusService(
                    httpService, new String[]{"L94", "P001", "208.00000"}));
        }

        @Override
        public List<StatusCheckService> getServices() {
            return statusCheckServices;
        }

        private List<StatusCheckService> statusCheckServices;

    }

}
