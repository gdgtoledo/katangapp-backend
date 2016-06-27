package es.craftsmanship.toledo.katangapp.internal.services;

import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.business.services.StatusCheckService;
import es.craftsmanship.toledo.katangapp.business.services.StatusCheckServiceDiscoveryManager;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;

import com.google.common.base.Stopwatch;

import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.Json;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author mdelapenya
 */
public class KatangaServiceDiscoveryManager
	implements StatusCheckServiceDiscoveryManager {

	public KatangaServiceDiscoveryManager(HttpService httpService) {
		this.httpService = httpService;

		this.statusCheckServices = new ArrayList<>();

		statusCheckServices.add(new UnautoStatusService(this.httpService));
	}

	public Promise<JsonNode> processServices() {
		List<Promise<JsonNode>> resultsPromiseList = new ArrayList<>();

		for (StatusCheckService service : statusCheckServices) {
			resultsPromiseList.add(observeEllapsedTime(service));
		}

		Promise<List<JsonNode>> sequence = Promise.sequence(resultsPromiseList);

		return sequence.map(new Function<List<JsonNode>, JsonNode>() {

			@Override
			public JsonNode apply(List<JsonNode> jsonNodes) throws Throwable {
				return Json.toJson(jsonNodes);
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

	private List<StatusCheckService> statusCheckServices;

	private final HttpService httpService;

}