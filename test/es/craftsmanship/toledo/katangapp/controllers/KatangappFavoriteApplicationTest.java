package es.craftsmanship.toledo.katangapp.controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentType;
import static play.test.Helpers.status;

import es.craftsmanship.toledo.katangapp.mocks.MockController;
import es.craftsmanship.toledo.katangapp.test.SpecsContants;

import org.junit.Test;

import play.test.WithApplication;

import play.libs.F;

import play.mvc.Result;

/**
 * @author manudevelopia
 */
public class KatangappFavoriteApplicationTest extends WithApplication {

	@Test
	public void testFavorite() {
		String busStopId = "294";

		MockController.mockRequest(true);

		KatangappFavoriteApplication katangappFavoriteApplication =
			new KatangappFavoriteApplication();

		F.Promise<Result> resultPromise = katangappFavoriteApplication.favorite(
			busStopId);

		Result result = resultPromise.get(SpecsContants.TIMEOUT);

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
	}

}