package es.craftsmanship.toledo.katangapp.internal.controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;

import static play.test.Helpers.contentType;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;
import static play.test.Helpers.status;

import es.craftsmanship.toledo.katangapp.test.AssertUtils;

import org.junit.Test;

import play.mvc.Result;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class KatangappApiApplicationTest extends WithApplication {

	@Test
	public void testBusStop() {
		Result result = route(fakeRequest(GET, "/api/busStops/P003"));

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

	@Test
	public void testBusStopNotFound() {
		Result result = route(fakeRequest(GET, "/api/busStops/notfound"));

		assertThat(status(result)).isEqualTo(NOT_FOUND);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

	@Test
	public void testBusStops() {
		Result result = route(fakeRequest(GET, "/api/busStops"));

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

	@Test
	public void testBusStopsWithPrettyPrint() {
		Result result = route(fakeRequest(GET, "/api/busStops?prettyPrint=1"));

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

	@Test
	public void testBusStopWithPrettyPrint() {
		Result result = route(fakeRequest(
			GET, "/api/busStops/P003?prettyPrint=1"));

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

	@Test
	public void testRoute() {
		Result result = route(fakeRequest(GET, "/api/routes/L02"));

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

	@Test
	public void testRouteNotFound() {
		Result result = route(fakeRequest(GET, "/api/routes/notfound"));

		assertThat(status(result)).isEqualTo(NOT_FOUND);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

	@Test
	public void testRoutes() {
		Result result = route(fakeRequest(GET, "/api/routes"));

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

	@Test
	public void testRoutesWithPrettyPrint() {
		Result result = route(fakeRequest(GET, "/api/routes?prettyPrint=1"));

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

	@Test
	public void testRouteWithPrettyPrint() {
		Result result = route(
			fakeRequest(GET, "/api/routes/L02?prettyPrint=1"));

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

}
