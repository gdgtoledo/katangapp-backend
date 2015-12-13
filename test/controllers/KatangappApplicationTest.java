package controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import business.mocks.MockBusStopStore;
import business.mocks.MockBusStopsFinder;
import business.mocks.MockController;
import business.mocks.MockHttpService;

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
	public void testIndex() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();

		String latitude = String.valueOf(puertaBisagra.getLatitude());
		String longitude = String.valueOf(puertaBisagra.getLongitude());
		int radius = 1000;

		MockController.mockRequest(false);

		Result result = KatangappApplication.index(latitude, longitude, radius);

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testIndexWithPrettyPrint() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();

		String latitude = String.valueOf(puertaBisagra.getLatitude());
		String longitude = String.valueOf(puertaBisagra.getLongitude());
		int radius = 1000;

		MockController.mockRequest(true);

		Result result = KatangappApplication.index(latitude, longitude, radius);

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	@Test
	public void testUnauto() {
		MockBusStopStore mockBusStopStore = new MockBusStopStore();

		for (Map.Entry<String, BusStop> stopEntry :
				mockBusStopStore.entrySet()) {

			BusStop busStop = stopEntry.getValue();

			Result result = KatangappApplication.unauto(
				busStop.getRouteId(), busStop.getId(), busStop.getOrder());

			assertThat(status(result)).isEqualTo(OK);
			assertThat(contentType(result)).isEqualTo("text/plain");

			break;
		}

	}

}
