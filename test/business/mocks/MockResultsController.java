package business.mocks;

import org.mockito.Mockito;
import play.mvc.Http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mdelapenya
 */
public class MockResultsController {

	public static void mockRequest(boolean prettyPrint) {
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
	}

}
