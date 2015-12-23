package business.exception;

import business.JsonNodeFactory;

import com.fasterxml.jackson.databind.JsonNode;

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