package controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import business.mocks.MockController;

import internal.business.store.KatangappStore;

import models.BusStop;

import org.junit.Test;

import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class JsonPrettyPrinterTest {

	@Test
	public void testDoNotPrettyPrint() throws Exception {
		Http.Request request = MockController.mockRequest(false);

		BusStop busStop = KatangappStore.getInstance().getBusStop("P001");

		JsonPrettyPrinter prettyPrinter = new JsonPrettyPrinter(
			request, Json.toJson(busStop));

		Result result = prettyPrinter.prettyPrintWhenNeeded();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");

		String expectedResult =
			"{\"address\":\"Corralillo de San Miguel, Toledo, España\"," +
				"\"id\":\"P001\",\"latitude\":39.858966," +
				"\"longitude\":-4.020902,\"order\":\"208.00000\"," +
				"\"routeId\":\"L94\"}";

		assertThat(contentAsString(result)).isEqualTo(expectedResult);
	}

	@Test
	public void testPrettyPrint() throws Exception {
		Http.Request request = MockController.mockRequest(true);

		BusStop busStop = KatangappStore.getInstance().getBusStop("P001");

		JsonPrettyPrinter prettyPrinter = new JsonPrettyPrinter(
			request, Json.toJson(busStop));

		Result result = prettyPrinter.prettyPrintWhenNeeded();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");

		String expectedResult = "{\n" +
			"  \"address\" : \"Corralillo de San Miguel, Toledo, España\",\n" +
			"  \"id\" : \"P001\",\n" +
			"  \"latitude\" : 39.858966,\n" +
			"  \"longitude\" : -4.020902,\n" +
			"  \"order\" : \"208.00000\",\n" +
			"  \"routeId\" : \"L94\"\n" +
			"}";

		assertThat(contentAsString(result)).isEqualTo(expectedResult);
	}

}