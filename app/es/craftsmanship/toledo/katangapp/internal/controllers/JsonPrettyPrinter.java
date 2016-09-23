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

package es.craftsmanship.toledo.katangapp.internal.controllers;

import es.craftsmanship.toledo.katangapp.api.controllers.PrettyPrinter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.nio.charset.Charset;

import java.util.Map;

import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

/**
 * @author mdelapenya
 */
public class JsonPrettyPrinter implements PrettyPrinter {

	public JsonPrettyPrinter(Http.Request request, JsonNode jsonNode) {
		this.request = request;
		this.jsonNode = jsonNode;
	}

	@Override
	public Result prettyPrint() {
		if (isPrettyPrint()) {
			ObjectMapper mapper = new ObjectMapper();

			final ObjectWriter objectWriter =
				mapper.writerWithDefaultPrettyPrinter();

			try {
				String prettyPrint = objectWriter.writeValueAsString(jsonNode);

				return Results.ok(prettyPrint).as(CONTENT_TYPE);
			}
			catch (JsonProcessingException e) {
				// fall back to default JSON print
			}
		}

		return Results.ok(jsonNode);
	}

	private boolean isPrettyPrint() {
		Map<String, String[]> queryStringParametersMap = request.queryString();

		boolean isPrettyPrint = queryStringParametersMap.containsKey(
			"prettyPrint");

		if (isPrettyPrint) {
			final String[] pretties = queryStringParametersMap.get(
				"prettyPrint");

			if (pretties[0].equalsIgnoreCase("true") ||
				pretties[0].equalsIgnoreCase("1")) {

				return true;
			}
		}

		return false;
	}

	private final Http.Request request;
	private final JsonNode jsonNode;

	private static final String CONTENT_TYPE =
		"application/json; charset=" + Charset.forName("UTF-8");

}