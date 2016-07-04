package es.craftsmanship.toledo.katangapp.internal.controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.mvc.Http.Status.OK;

import static play.test.Helpers.contentType;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;
import static play.test.Helpers.status;

import es.craftsmanship.toledo.katangapp.test.AssertUtils;

import org.junit.Test;

import play.test.WithApplication;

import play.mvc.Result;

/**
 * @author manudevelopia
 */
public class KatangappFavoriteApplicationTest extends WithApplication {

	@Test
	public void testFavorite() {
		String busStopId = "294";

		Result result = route(fakeRequest(GET, "/favorite/" + busStopId));

		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("application/json");
		AssertUtils.assertCORS(result);
	}

}