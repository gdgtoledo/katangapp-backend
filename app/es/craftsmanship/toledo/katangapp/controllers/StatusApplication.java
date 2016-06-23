package es.craftsmanship.toledo.katangapp.controllers;

import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.internal.controllers.JsonPrettyPrinter;
import es.craftsmanship.toledo.katangapp.internal.services.KatangaServiceDiscoveryManager;
import es.craftsmanship.toledo.katangapp.services.StatusCheckServiceDiscoveryManager;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.inject.Inject;

import play.libs.F.Function;
import play.libs.F.Promise;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class StatusApplication extends Controller {

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
				PrettyPrinter prettyPrinter = new JsonPrettyPrinter(
					request(), jsonNode);

				return prettyPrinter.prettyPrint();
			}

		});
	}

    private final StatusCheckServiceDiscoveryManager serviceDiscoveryManager;

}
