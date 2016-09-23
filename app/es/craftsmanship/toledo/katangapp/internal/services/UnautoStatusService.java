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

import com.fasterxml.jackson.databind.JsonNode;

import com.google.inject.Inject;

import play.libs.F.Promise;
import play.libs.Json;

/**
 * @author mdelapenya
 */
public class UnautoStatusService implements StatusCheckService {

	@Inject
	public UnautoStatusService(HttpService httpService) {
		this.httpService = httpService;

		this.payload = new String[]{"L94", "P001", "208.00000"};
	}

	@Override
	public Promise<JsonNode> healthCheck() {
		String[] params = (String[])payload;

		Promise<String> httpPromise = httpService.get(params);

		Promise<JsonNode> resultPromise =
			httpPromise.map(
				result -> {
					if (result == null || result.isEmpty()) {
						return Json.toJson("Unauto: KO");
					}

					return Json.toJson("Unauto: OK");
				}
		);

		return resultPromise;
	}

	private HttpService httpService;
	private Object payload;

}
