package controllers;

import static org.fest.assertions.Assertions.assertThat;

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
		katangappApplication = new KatangappApplication();

		final MockHttpService mockHttpService = new MockHttpService("P001");

		katangappApplication.setBusStopFinder(
			MockBusStopsFinder.mockFinder(mockHttpService));

		katangappApplication.setHttpService(mockHttpService);

	}

	@Test
	public void testMain() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();

		String latitude = String.valueOf(puertaBisagra.getLatitude());
		String longitude = String.valueOf(puertaBisagra.getLongitude());
		int radius = 1000;

		MockController.mockRequest(false);

		Result result = katangappApplication.main(latitude, longitude, radius);

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

		Result result = katangappApplication.main(latitude, longitude, radius);

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testUnauto() {
		Store busStopStore = KatangappStore.getInstance();

		Map<String, BusStop> busStopMap = busStopStore.getBusStopStore();

		for (Map.Entry<String, BusStop> stopEntry : busStopMap.entrySet()) {
			BusStop busStop = stopEntry.getValue();

			Result result = katangappApplication.unauto(
				busStop.getRouteId(), busStop.getId(), busStop.getOrder());

			assertThat(status(result)).isEqualTo(OK);
			assertThat(contentType(result)).isEqualTo("text/plain");

			break;
		}
	}

	private KatangappApplication katangappApplication;

}
