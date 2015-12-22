package internal.business.http;

import business.http.HttpService;

import java.nio.charset.Charset;
import java.text.MessageFormat;

import play.libs.ws.WS;
import play.libs.ws.WSResponse;
import play.libs.F.Function;
import play.libs.F.Promise;

/**
 * @author mdelapenya
 */
public class UnautoHttpService implements HttpService {

	public UnautoHttpService() {
	}

	@Override
	public String get(String... params) {
		if (params == null || params.length != 3) {
			throw new IllegalArgumentException(
				"Cannot invoke the service " + params.length + " parameters");
		}

		String idl = params[0];
		String idp = params[1];
		String ido = params[2];

		return get(idl, idp, ido);
	}

	private String get(String idl, String idp, String ido) {
		String url = MessageFormat.format(ENDPOINT, idl, idp, ido);

		Promise<String> documentPromise = WS.url(url).get().map(
			new Function<WSResponse, String>() {

				public String apply(WSResponse response) {
					byte[] bytes = response.asByteArray();

					return new String(bytes, Charset.forName("UTF-8"));
				}

			}
		);

		return documentPromise.get(TIMEOUT);
	}

	private static final String ENDPOINT =
		"http://unauto.twa.es/code/getparadas.php?idl={0}&idp={1}&ido={2}";

	private static final long TIMEOUT = 5000;

}
