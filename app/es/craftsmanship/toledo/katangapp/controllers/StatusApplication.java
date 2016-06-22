package es.craftsmanship.toledo.katangapp.controllers;

import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.internal.services.UnautoStatusService;
import es.craftsmanship.toledo.katangapp.services.StatusCheckService;
import es.craftsmanship.toledo.katangapp.services.StatusCheckServiceDiscoveryManager;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

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
            resultsPromiseList.add(service.healthCheck());
        }

        Promise<List<JsonNode>> sequence = Promise.sequence(resultsPromiseList);

        return sequence.map(new Function<List<JsonNode>, Result>() {

            @Override
            public Result apply(List<JsonNode> jsonNodes) throws Throwable {
                return ok(Json.toJson(jsonNodes));
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
