package es.craftsmanship.toledo.katangapp.internal.controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.OK;

import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.internal.BusStopsFinder;
import es.craftsmanship.toledo.katangapp.internal.algorithm.SegmentsAlgorithm;
import es.craftsmanship.toledo.katangapp.internal.controllers.KatangappApplication;
import es.craftsmanship.toledo.katangapp.internal.parser.HTMLParser;
import es.craftsmanship.toledo.katangapp.mocks.MockController;
import es.craftsmanship.toledo.katangapp.mocks.MockHttpService;
import es.craftsmanship.toledo.katangapp.models.Point;
import es.craftsmanship.toledo.katangapp.models.TestPointFactory;
import es.craftsmanship.toledo.katangapp.test.SpecsContants;

import org.junit.Before;
import org.junit.Test;

import play.libs.F.Promise;

import play.mvc.Result;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class KatangappApplicationTest extends WithApplication {

	@Before
	public void setUp() {
		final MockHttpService mockHttpService = new MockHttpService("P001");

		Finder busStopFinder = new BusStopsFinder(
			new SegmentsAlgorithm(), new HTMLParser(), mockHttpService);

		katangappApplication = new KatangappApplication(busStopFinder);
	}

	@Test
	public void testMain() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();

		String latitude = String.valueOf(puertaBisagra.getLatitude());
		String longitude = String.valueOf(puertaBisagra.getLongitude());
		int radius = 1000;

		MockController.mockRequest(false);

		Promise<Result> resultPromise = katangappApplication.main(
			latitude, longitude, radius);

		Result result = resultPromise.get(SpecsContants.TIMEOUT);

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

		Promise<Result> resultPromise = katangappApplication.main(
			latitude, longitude, radius);

		Result result = resultPromise.get(SpecsContants.TIMEOUT);

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	private KatangappApplication katangappApplication;

}