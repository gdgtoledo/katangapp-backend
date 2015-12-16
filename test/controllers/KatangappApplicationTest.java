package controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.NOT_FOUND;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import business.mocks.MockBusStopsFinder;
import business.mocks.MockController;
import business.mocks.MockHttpService;
import business.store.Store;

import internal.business.store.KatangappStore;

import models.BusStop;
import models.Point;
import models.TestPointFactory;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import play.test.WithApplication;

import java.util.Map;

/**
 * @author mdelapenya
 */
public class KatangappApplicationTest extends WithApplication {

	@Before
	public void setUp() {
		final MockHttpService mockHttpService = new MockHttpService("P001");

		KatangappApplication.setBusStopFinder(
			MockBusStopsFinder.mockFinder(mockHttpService));

		KatangappApplication.setHttpService(mockHttpService);
	}

	@Test
	public void testBusStop() {
		Result result = KatangappApplication.busStop("P003");

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testBusStopNotFound() {
		Result result = KatangappApplication.busStop("notfound");

		assertThat(status(result)).isEqualTo(NOT_FOUND);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testBusStops() {
		Result result = KatangappApplication.busStops();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testMain() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();

		String latitude = String.valueOf(puertaBisagra.getLatitude());
		String longitude = String.valueOf(puertaBisagra.getLongitude());
		int radius = 1000;

		MockController.mockRequest(false);

		Result result = KatangappApplication.main(latitude, longitude, radius);

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testMainWithPrettyPrint() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();

		String latitude = String.valueOf(puertaBisagra.getLatitude());
		String longitude = String.valueOf(puertaBisagra.getLongitude());
		int radius = 1000;

		MockController.mockRequest(true);

		Result result = KatangappApplication.main(latitude, longitude, radius);

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRoute() {
		Result result = KatangappApplication.route("L02");

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRouteNotFound() {
		Result result = KatangappApplication.route("notfound");

		assertThat(status(result)).isEqualTo(NOT_FOUND);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testRoutes() {
		Result result = KatangappApplication.routes();

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testUnauto() {
		Store busStopStore = KatangappStore.getInstance();

		for (Map.Entry<String, BusStop> stopEntry : busStopStore.entrySet()) {
			BusStop busStop = stopEntry.getValue();

			Result result = KatangappApplication.unauto(
				busStop.getRouteId(), busStop.getId(), busStop.getOrder());

			assertThat(status(result)).isEqualTo(OK);
			assertThat(contentType(result)).isEqualTo("text/plain");

			break;
		}

	}

}
