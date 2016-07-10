package es.craftsmanship.toledo.katangapp.api.exception;

import com.fasterxml.jackson.databind.node.TextNode;
import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;

/**
 * @author mdelapenya
 */
public class APIException extends Exception {

	public APIException(String apiMesage) {
		apiError = Json.newObject().set("message", new TextNode(apiMesage));
	}

	public JsonNode getApiError() {
		return apiError;
	}

	private JsonNode apiError;

}