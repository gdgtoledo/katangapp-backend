package es.craftsmanship.toledo.katangapp.api.controllers;

import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;

import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.GET;
import static play.test.Helpers.route;

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

		AssertUtils.assertTCK(result, OK);
	}

	@Test
	public void testBusStopNotFound() {
		Result result = route(fakeRequest(GET, "/api/busStops/notfound"));

		AssertUtils.assertTCK(result, NOT_FOUND);
	}

	@Test
	public void testBusStops() {
		Result result = route(fakeRequest(GET, "/api/busStops"));

		AssertUtils.assertTCK(result, OK);
	}

	@Test
	public void testBusStopsWithPrettyPrint() {
		Result result = route(fakeRequest(GET, "/api/busStops?prettyPrint=1"));

		AssertUtils.assertTCK(result, OK);
	}

	@Test
	public void testBusStopWithPrettyPrint() {
		Result result = route(fakeRequest(
			GET, "/api/busStops/P003?prettyPrint=1"));

		AssertUtils.assertTCK(result, OK);
	}

	@Test
	public void testRoute() {
		Result result = route(fakeRequest(GET, "/api/routes/L02"));

		AssertUtils.assertTCK(result, OK);
	}

	@Test
	public void testRouteNotFound() {
		Result result = route(fakeRequest(GET, "/api/routes/notfound"));

		AssertUtils.assertTCK(result, NOT_FOUND);
	}

	@Test
	public void testRoutes() {
		Result result = route(fakeRequest(GET, "/api/routes"));

		AssertUtils.assertTCK(result, OK);
	}

	@Test
	public void testRoutesWithPrettyPrint() {
		Result result = route(fakeRequest(GET, "/api/routes?prettyPrint=1"));

		AssertUtils.assertTCK(result, OK);
	}

	@Test
	public void testRouteWithPrettyPrint() {
		Result result = route(
			fakeRequest(GET, "/api/routes/L02?prettyPrint=1"));

		AssertUtils.assertTCK(result, OK);
	}

}
