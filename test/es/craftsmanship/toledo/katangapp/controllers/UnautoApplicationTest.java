package es.craftsmanship.toledo.katangapp.controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.business.store.Store;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.mocks.MockHttpService;
import es.craftsmanship.toledo.katangapp.models.BusStop;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import play.libs.F.Promise;

import play.mvc.Result;
import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class UnautoApplicationTest extends WithApplication {

	@Before
	public void setUp() {
		final MockHttpService mockHttpService = new MockHttpService("P001");

		unautoApplication = new UnautoApplication(mockHttpService);
	}

	@Test
	public void testUnauto() {
		Store busStopStore = KatangappStore.getInstance();

		Map<String, BusStop> busStopMap = busStopStore.getBusStopStore();

		for (Map.Entry<String, BusStop> stopEntry : busStopMap.entrySet()) {
			BusStop busStop = stopEntry.getValue();

			Promise<Result> resultPromise = unautoApplication.unauto(
				busStop.getRouteId(), busStop.getId(), busStop.getOrder());

			Result result = resultPromise.get(HttpService.TIMEOUT);

			assertThat(status(result)).isEqualTo(OK);
			assertThat(contentType(result)).isEqualTo("text/plain");

			break;
		}
	}

	private UnautoApplication unautoApplication;

}