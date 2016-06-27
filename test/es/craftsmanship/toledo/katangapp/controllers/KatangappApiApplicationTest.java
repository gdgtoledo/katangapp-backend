package es.craftsmanship.toledo.katangapp.controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;

import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import es.craftsmanship.toledo.katangapp.internal.controllers.KatangappApiApplication;
import es.craftsmanship.toledo.katangapp.mocks.MockController;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class KatangappApiApplicationTest extends WithApplication {

	@Before
	public void setUp() {
		katangappApiApplication = new KatangappApiApplication();

		MockController.mockRequest(false);
	}

	@Test
	public void testBusStop() {
		Result result = katangappApiApplication.busStop("P003");

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testBusStopNotFound() {
		Result result = katangappApiApplication.busStop("notfound");

		assertThat(status(result)).isEqualTo(NOT_FOUND);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testBusStops() {
		Result result = katangappApiApplication.busStops();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testBusStopsWithPrettyPrint() {
		MockController.mockRequest(true);

		Result result = katangappApiApplication.busStops();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testBusStopWithPrettyPrint() {
		MockController.mockRequest(true);

		Result result = katangappApiApplication.busStop("P003");

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRoute() {
		Result result = katangappApiApplication.route("L02");

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRouteNotFound() {
		Result result = katangappApiApplication.route("notfound");

		assertThat(status(result)).isEqualTo(NOT_FOUND);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRoutes() {
		Result result = katangappApiApplication.routes();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRoutesWithPrettyPrint() {
		MockController.mockRequest(true);

		Result result = katangappApiApplication.routes();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRouteWithPrettyPrint() {
		MockController.mockRequest(true);

		Result result = katangappApiApplication.route("L02");

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	private KatangappApiApplication katangappApiApplication;

}
