package es.craftsmanship.toledo.katangapp.mocks;

import es.craftsmanship.toledo.katangapp.business.IOTestUtils;
import es.craftsmanship.toledo.katangapp.internal.http.UnautoHttpService;

import play.libs.F.Promise;
import play.libs.F.Function0;

/**
 * This class mocks the original Http service from Unauto, returning always a
 * predefined HTML response, which is located in the test/resources/sample.html
 * file. This file, and each one named as sample-BUSSTOPID, has been generated
 * reading the response of invoking the service:
 * <code>http://unauto.twa.es/code/getparadas.php?idl=X&idp=Y&ido=Z</code>,
 * where X, Y and Z are the variables describing a bus stop in a route.
 *
 * @author mdelapenya
 */
public class MockHttpService extends UnautoHttpService {

	/**
	 * Create a mock implementation of the HTTP service, defining a bus stops id
	 * as the one to be used for reading pre-defined expectations.
	 *
	 * @param idp The bus stop id.
	 */
	public MockHttpService(String idp) {
		this.idp = idp;
	}

	/**
	 * Predefine a string representation for the HTTP service, based on a
	 * previously fetched response. It will read the <code>idp</code> attribute
	 * to read the test file with the expectation for the HTTP service.
	 *
	 * @param arg Arguments to be passed to the HTTP service, that won't be
	 *            used.
	 *
	 * @return the string representation of the HTTP service.
	 */
	@Override
	public Promise<String> get(String... arg) {
		validate(arg);

		return Promise.promise(new Function0<String>() {

			@Override
			public String apply() throws Throwable {
				return IOTestUtils.readFile("sample-" + idp + ".html");
			}

		});
	}

	private String idp;

}
