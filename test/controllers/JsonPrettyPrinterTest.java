package controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import business.mocks.MockController;

import internal.business.store.KatangappStore;
import internal.test.SpecsContants;

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

		assertThat(contentAsString(result)).
			isEqualTo(SpecsContants.BUS_STOP_JSON);
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

		assertThat(contentAsString(result)).
			isEqualTo(SpecsContants.BUS_STOP_PRETTIFIED_JSON);
	}

}