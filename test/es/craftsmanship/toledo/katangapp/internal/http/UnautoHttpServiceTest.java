package es.craftsmanship.toledo.katangapp.internal.http;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;

import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.mocks.MockHttpService;

import org.junit.Test;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class UnautoHttpServiceTest extends WithApplication {

	@Test
	public void testGet() throws Exception {
		String idl = "41";
		String idp = "P001";
		String ido = "1.00000";

		final HttpService httpService = new MockHttpService(idp);

		String response = httpService.get(idl, idp, ido);

		assertThat(response).isNotEmpty();
	}

	@Test
	public void testGetWithEmptyParameters() throws Exception {
		final HttpService httpService = new MockHttpService("P001");

		try {
			httpService.get(new String[0]);

			fail();
		}
		catch (IllegalArgumentException iae) {
			assertThat(iae.getMessage()).isEqualTo(
				"Wrong service invocation: it only accepts three parameters");
		}
	}

	@Test
	public void testGetWithNullParameters() throws Exception {
		final HttpService httpService = new MockHttpService("P001");

		try {
			String[] params = null;

			httpService.get(params);

			fail();
		}
		catch (IllegalArgumentException iae) {
			assertThat(iae.getMessage()).isEqualTo(
				"Wrong service invocation: it only accepts three parameters");
		}
	}

	@Test
	public void testGetWithParameterLengthGreaterThan3ShouldInvokeService()
		throws Exception {

		String idl = "41";
		String idp = "P001";
		String ido = "1.00000";

		final HttpService httpService = new MockHttpService(idp);

		try {
			httpService.get(idl, idp, ido, "never-mind");

			fail();
		}
		catch (IllegalArgumentException iae) {
			assertThat(iae.getMessage()).isEqualTo(
				"Wrong service invocation: it only accepts three parameters");
		}
	}

}
