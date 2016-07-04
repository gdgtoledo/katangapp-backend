package es.craftsmanship.toledo.katangapp.test;

import static org.fest.assertions.Assertions.assertThat;

import static play.test.Helpers.contentType;
import static play.test.Helpers.header;
import static play.test.Helpers.status;

import play.mvc.Http;
import play.mvc.Result;


/**
 * @author Manuel de la Peña
 */
public class AssertUtils {

	public static void assertTCK(Result result, int status) {
		assertTCK(result, "application/json", status);
	}

	public static void assertTCK(
		Result result, String contentType, int status) {

		assertThat(status(result)).isEqualTo(status);
		assertThat(contentType(result)).isEqualTo(contentType);
		assertThat(
			header(Http.HeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, result)
		).contains("*");
	}

}