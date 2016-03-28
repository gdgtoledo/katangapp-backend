package es.craftsmanship.toledo.katangapp.internal.http;

import static org.fest.assertions.Assertions.assertThat;

import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.mocks.MockHttpService;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import play.libs.F.Promise;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class UnautoHttpServiceTest extends WithApplication {

	@Rule
	public ExpectedException thrownException = ExpectedException.none();

	@Test
	public void testGet() throws Exception {
		String idl = "41";
		String idp = "P001";
		String ido = "1.00000";

		final HttpService httpService = new MockHttpService(idp);

		Promise<String> response = httpService.get(idl, idp, ido);

		assertThat(response.get(HttpService.TIMEOUT)).isNotEmpty();
	}

	@Test
	public void testGetWithEmptyParameters() throws Exception {
		thrownException.expect(IllegalArgumentException.class);
		thrownException.expectMessage(
			"Wrong service invocation: it only accepts three parameters");

		final HttpService httpService = new MockHttpService("P001");

		httpService.get(new String[0]);
	}

	@Test
	public void testGetWithNullParameters() throws Exception {
		thrownException.expect(IllegalArgumentException.class);
		thrownException.expectMessage(
			"Wrong service invocation: it only accepts three parameters");

		final HttpService httpService = new MockHttpService("P001");

		String[] params = null;

		httpService.get(params);
	}

	@Test
	public void testGetWithParameterLengthGreaterThan3ShouldInvokeService()
		throws Exception {

		thrownException.expect(IllegalArgumentException.class);
		thrownException.expectMessage(
			"Wrong service invocation: it only accepts three parameters");

		String idl = "41";
		String idp = "P001";
		String ido = "1.00000";

		final HttpService httpService = new MockHttpService(idp);

		httpService.get(idl, idp, ido, "never-mind");
	}

}
