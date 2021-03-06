package es.craftsmanship.toledo.katangapp.api.controllers;

import static play.mvc.Http.Status.OK;

import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;

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

		AssertUtils.assertTCK(result, OK);
	}

	@Test
	public void testFavoriteWithPrettyPrint() {
		String busStopId = "294";

		Result result = route(
			fakeRequest(GET, "/favorite/" + busStopId + "?prettyPrint=1"));

		AssertUtils.assertTCK(result, OK);
	}

}