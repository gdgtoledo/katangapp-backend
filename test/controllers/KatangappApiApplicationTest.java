package controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import business.mocks.MockBusStopsFinder;
import business.mocks.MockHttpService;

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
		final MockHttpService mockHttpService = new MockHttpService("P001");

		KatangappApiApplication.setBusStopFinder(
			MockBusStopsFinder.mockFinder(mockHttpService));
	}

	@Test
	public void testBusStop() {
		Result result = KatangappApiApplication.busStop("P003");

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testBusStopNotFound() {
		Result result = KatangappApiApplication.busStop("notfound");

		assertThat(status(result)).isEqualTo(NOT_FOUND);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testBusStops() {
		Result result = KatangappApiApplication.busStops();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRoute() {
		Result result = KatangappApiApplication.route("L02");

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRouteNotFound() {
		Result result = KatangappApiApplication.route("notfound");

		assertThat(status(result)).isEqualTo(NOT_FOUND);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRoutes() {
		Result result = KatangappApiApplication.routes();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

}
