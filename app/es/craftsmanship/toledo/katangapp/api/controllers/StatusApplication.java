package es.craftsmanship.toledo.katangapp.api.controllers;

import es.craftsmanship.toledo.katangapp.api.http.HttpService;
import es.craftsmanship.toledo.katangapp.api.services.StatusCheckServiceDiscoveryManager;
import es.craftsmanship.toledo.katangapp.internal.controllers.BaseKatangaApplication;
import es.craftsmanship.toledo.katangapp.internal.services.KatangaServiceDiscoveryManager;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.inject.Inject;

import play.libs.F.Function;
import play.libs.F.Promise;

import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class StatusApplication extends BaseKatangaApplication {

    @Inject
    public StatusApplication(HttpService httpService) {
        this.serviceDiscoveryManager =
            new KatangaServiceDiscoveryManager(httpService);
    }

    public Promise<Result> status() {
		Promise<JsonNode> jsonNodePromise =
			serviceDiscoveryManager.processServices();

		return jsonNodePromise.map(new Function<JsonNode, Result>() {

			@Override
			public Result apply(JsonNode jsonNode) throws Throwable {
				return prettyPrint(jsonNode);
			}

		});
	}

    private final StatusCheckServiceDiscoveryManager serviceDiscoveryManager;

}