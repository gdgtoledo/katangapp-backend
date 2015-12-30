package es.craftsmanship.toledo.katangapp.business.exception;

import com.fasterxml.jackson.databind.JsonNode;

import es.craftsmanship.toledo.katangapp.business.JsonNodeFactory;

/**
 * @author mdelapenya
 */
public class APIException extends Exception {

	public APIException(String apiMesage) {
		apiError = JsonNodeFactory.getTextNode("message", apiMesage);
	}

	public JsonNode getApiError() {
		return apiError;
	}

	private JsonNode apiError;

}