package es.craftsmanship.toledo.katangapp.internal.controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.OK;

import static play.test.Helpers.contentAsString;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import es.craftsmanship.toledo.katangapp.business.controllers.PrettyPrinter;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.test.SpecsContants;
import es.craftsmanship.toledo.katangapp.models.BusStop;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import org.mockito.Mockito;

import play.libs.Json;

import play.mvc.Http;
import play.mvc.Result;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class JsonPrettyPrinterTest extends WithApplication {

	@Test
	public void testDoNotPrettyPrint() throws Exception {
		Http.Request request = mockController.mockRequest(false);

		BusStop busStop = KatangappStore.getInstance().getBusStop("P001");

		PrettyPrinter prettyPrinter = new JsonPrettyPrinter(
			request, Json.toJson(busStop));

		Result result = prettyPrinter.prettyPrint();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");

		assertThat(contentAsString(result)).
			isEqualTo(SpecsContants.BUS_STOP_JSON);
	}

	@Test
	public void testPrettyPrint() throws Exception {
		Http.Request request = mockController.mockRequest(true);

		BusStop busStop = KatangappStore.getInstance().getBusStop("P001");

		PrettyPrinter prettyPrinter = new JsonPrettyPrinter(
			request, Json.toJson(busStop));

		Result result = prettyPrinter.prettyPrint();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");

		assertThat(contentAsString(result)).
			isEqualTo(SpecsContants.BUS_STOP_PRETTIFIED_JSON);
	}

	private MockController mockController = new MockController();

	/**
	 * This class offers methods to mock an HTTP request.
	 *
	 * @author mdelapenya
	 */
	private class MockController {

		/**
		 * Mocks an HTTP request adding a queryString parameter based on input
		 * parameter.
		 *
		 * @param prettyPrint Whether a <code>prettyPrint</code> HTTP parameter
		 *                    is added or not to the HTTP request.
		 *
		 * @return the mocked request
		 */
		public Http.Request mockRequest(boolean prettyPrint) {
			Long id = 2L;

			play.api.mvc.RequestHeader header = Mockito.mock(
				play.api.mvc.RequestHeader.class);

			Http.Request request = Mockito.mock(Http.Request.class);

			if (prettyPrint) {
				Map<String, String[]> mockQueryString = new HashMap<>();

				mockQueryString.put("prettyPrint", new String[] {"true"});

				Mockito.when(request.queryString()).thenReturn(mockQueryString);
			}

			Map<String, String> flashData = Collections.emptyMap();
			Map<String, Object> argData = Collections.emptyMap();

			Http.Context context = new Http.Context(
				id, header, request, flashData, flashData, argData);

			Http.Context.current.set(context);

			return request;
		}

	}
}