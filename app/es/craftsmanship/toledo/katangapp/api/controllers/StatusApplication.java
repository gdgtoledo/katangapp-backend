package es.craftsmanship.toledo.katangapp.api.controllers;

import es.craftsmanship.toledo.katangapp.api.http.HttpService;
import es.craftsmanship.toledo.katangapp.api.services.StatusCheckServiceDiscoveryManager;
import es.craftsmanship.toledo.katangapp.internal.controllers.BaseKatangaApplication;
import es.craftsmanship.toledo.katangapp.internal.services.KatangaServiceDiscoveryManager;

import com.fasterxml.jackson.databind.JsonNode;

import com.google.inject.Inject;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import play.libs.F.Function;
import play.libs.F.Promise;

import play.mvc.Result;

/**
 * @author mdelapenya
 */
@Api(
	value = "/status",
	description = "Health check endpoint of the application, which will" +
		" return current status of all managed services"
)
public class StatusApplication extends BaseKatangaApplication {

    @Inject
    public StatusApplication(HttpService httpService) {
        this.serviceDiscoveryManager =
            new KatangaServiceDiscoveryManager(httpService);
    }

	@ApiOperation(
		httpMethod = "GET",
		notes = "Executes a sanity check against all managed services for the" +
			" application to work, measuring the elapsed time to perform it." +
			" Each service will perform the sanity check in different ways," +
			" and all of them will be decorated with the resulting time of" +
			" the operation.",
		produces = "application/json",
		response = JsonNode.class,
		responseContainer = "Array",
		value = "Returns the current status of each managed service for the" +
			" application to work"
	)
	@ApiResponses(
		value = {
			@ApiResponse(
				code = 200, message = "Service: OK.",
				response = Result.class),
			@ApiResponse(
				code = 400, message = "Service: KO",
				response = Result.class),
		}
	)
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
