package es.craftsmanship.toledo.katangapp.test;

import static org.fest.assertions.Assertions.assertThat;

import static play.test.Helpers.header;

import play.mvc.Http;
import play.mvc.Result;


/**
 * @author Manuel de la Peña
 */
public class AssertUtils {

	public static void assertCORS(Result result) {
		assertThat(
			header(Http.HeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, result)
		).contains("*");
	}

}