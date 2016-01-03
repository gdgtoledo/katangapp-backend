package es.craftsmanship.toledo.katangapp.controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.OK;

import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.business.store.Store;
import es.craftsmanship.toledo.katangapp.internal.BusStopsFinder;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.mocks.MockController;
import es.craftsmanship.toledo.katangapp.mocks.MockHttpService;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.Point;
import es.craftsmanship.toledo.katangapp.models.TestPointFactory;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class KatangappApplicationTest extends WithApplication {

	@Before
	public void setUp() {
		final MockHttpService mockHttpService = new MockHttpService("P001");

		Finder busStopFinder = new BusStopsFinder(mockHttpService);

		katangappApplication = new KatangappApplication(busStopFinder);
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
