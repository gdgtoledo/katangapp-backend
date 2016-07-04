package es.craftsmanship.toledo.katangapp.internal.controllers;

import static play.mvc.Http.Status.OK;

import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.route;

import es.craftsmanship.toledo.katangapp.models.Point;
import es.craftsmanship.toledo.katangapp.models.TestPointFactory;
import es.craftsmanship.toledo.katangapp.test.AssertUtils;

import org.junit.Test;

import play.mvc.Result;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class KatangappApplicationTest extends WithApplication {

	@Test
	public void testMain() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();

		String latitude = String.valueOf(puertaBisagra.getLatitude());
		String longitude = String.valueOf(puertaBisagra.getLongitude());
		int radius = 1000;

		Result result = route(
			fakeRequest(
				GET,
				"/paradas?lt=" + latitude + "&ln=" + longitude + "&r=" +
					radius));

		AssertUtils.assertTCK(result, OK);
	}

	@Test
	public void testMainWithPrettyPrint() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();

		String latitude = String.valueOf(puertaBisagra.getLatitude());
		String longitude = String.valueOf(puertaBisagra.getLongitude());
		int radius = 1000;

		Result result = route(
			fakeRequest(
				GET,
				"/paradas?lt=" + latitude + "&ln=" + longitude + "&r=" +
					radius + "&prettyPrint=1"));

		AssertUtils.assertTCK(result, OK);
	}

}