package es.craftsmanship.toledo.katangapp.mocks;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.mockito.Mockito;

import play.mvc.Http;

/**
 * This class offers methods to mock an HTTP request.
 *
 * @author mdelapenya
 */
public class MockController {

	/**
	 * Mocks an HTTP request adding a queryString parameter based on input
	 * parameter.
	 *
	 * @param prettyPrint Whether a <code>prettyPrint</code> HTTP parameter is
	 *                    added or not to the HTTP request.
	 *
	 * @return the mocked request
	 */
	public static Http.Request mockRequest(boolean prettyPrint) {
		Long id = 2L;

		play.api.mvc.RequestHeader header = Mockito.mock(
			play.api.mvc.RequestHeader.class);

		Http.Request request = Mockito.mock(Http.Request.class);

		if (prettyPrint) {
			Map<String, String[]> mockQueryString = new HashMap<>();

			mockQueryString.put("prettyPrint", new String[] {"true"});

			Mockito.when(request.queryString()).thenReturn(mockQueryString);
		}

		Map<String, String> flashData = Collections.emptyMap();
		Map<String, Object> argData = Collections.emptyMap();

		Http.Context context = new Http.Context(
			id, header, request, flashData, flashData, argData);

		Http.Context.current.set(context);

		return request;
	}

}
