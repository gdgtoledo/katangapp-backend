/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.craftsmanship.toledo.katangapp.internal.services;

import es.craftsmanship.toledo.katangapp.api.http.HttpService;
import es.craftsmanship.toledo.katangapp.api.services.StatusCheckService;
import es.craftsmanship.toledo.katangapp.api.services.StatusCheckServiceDiscoveryManager;

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

		return sequence.map(Json::toJson);
	}

	private Promise<JsonNode> observeEllapsedTime(StatusCheckService service) {
		final Stopwatch stopwatch = Stopwatch.createStarted();

		Promise<JsonNode> healthCheckPromise = service.healthCheck();

		return healthCheckPromise.map(
			(Function<JsonNode, JsonNode>) jsonNode -> {
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
		);
	}

	private List<StatusCheckService> statusCheckServices;

	private final HttpService httpService;

}