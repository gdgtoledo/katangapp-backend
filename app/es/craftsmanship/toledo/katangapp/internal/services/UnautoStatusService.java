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
