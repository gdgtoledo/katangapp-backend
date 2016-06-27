package es.craftsmanship.toledo.katangapp.business.controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.internal.BusStopsFinder;
import es.craftsmanship.toledo.katangapp.internal.algorithm.SegmentsAlgorithm;
import es.craftsmanship.toledo.katangapp.internal.controllers.KatangappFavoriteApplication;
import es.craftsmanship.toledo.katangapp.internal.parser.HTMLParser;
import es.craftsmanship.toledo.katangapp.mocks.MockController;
import es.craftsmanship.toledo.katangapp.mocks.MockHttpService;
import es.craftsmanship.toledo.katangapp.test.SpecsContants;

import org.junit.Before;
import org.junit.Test;

import play.test.WithApplication;

import play.libs.F;

import play.mvc.Result;

/**
 * @author manudevelopia
 */
public class KatangappFavoriteApplicationTest extends WithApplication {

	@Before
	public void setUp() {
		final MockHttpService mockHttpService = new MockHttpService("P001");

		Finder busStopFinder = new BusStopsFinder(
			new SegmentsAlgorithm(), new HTMLParser(), mockHttpService);

		katangappFavoriteApplication = new KatangappFavoriteApplication(
			busStopFinder);
	}

	@Test
	public void testFavorite() {
		String busStopId = "294";

		MockController.mockRequest(true);

		F.Promise<Result> resultPromise = katangappFavoriteApplication.favorite(
			busStopId);

		Result result = resultPromise.get(SpecsContants.TIMEOUT);

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

	private KatangappFavoriteApplication katangappFavoriteApplication;

}