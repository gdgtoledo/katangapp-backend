/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.craftsmanship.toledo.katangapp.internal.http;

import es.craftsmanship.toledo.katangapp.api.http.HttpService;

import java.nio.charset.Charset;

import java.text.MessageFormat;

import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;
import play.libs.F.Promise;

/**
 * This class represents the former, old system for Toledo's bus routes. It will
 * invoke an HTTP request to the <code>ENDPOINT</code> defined as constant field
 * and will return a Promise with the response of that request.
 *
 * The <code>ENDPOINT</code> has the following representation:
 * <code>http://unauto.twa.es/code/getparadas.php?idl=X&idp=Y&ido=Z</code>,
 * where X, Y and Z are the variables describing a bus stop in a route.
 * 	<ul>
 * 	    <li>idl: the routeId of the bus stop</li>
 * 	    <li>idp: the id of the bus stop</li>
 * 	    <li>ido: the order id of the bus stop</li>
 * 	</ul>
 *
 * @author mdelapenya
 */
public class UnautoHttpService implements HttpService {

	public UnautoHttpService() {
	}

	@Override
	public void validate(String... params) {
		if (params == null || params.length != 3) {
			throw new IllegalArgumentException(
				"Wrong service invocation: it only accepts three parameters");
		}
	}

	/**
	 * Invokes the private method <code>get(String, String, String)</code>,
	 * which returns a String representation of the HTML produced by the former
	 * system.
	 *
	 * It will accept an array of parameters, only using the first three of
	 * them, which represent the following bus stop attributes in a route:
	 * <ul>
	 * 	    <li>param[0]: the routeId of the bus stop</li>
	 * 	    <li>param[1]: the id of the bus stop</li>
	 * 	    <li>param[2]: the order id of the bus stop</li>
	 * 	</ul>
	 *
	 * @param params An array of parameters to be parsed into the three
	 *               attributes of a bus stop in a route
	 *
	 * @return the response of the former system in HTML format
	 */
	@Override
	public Promise<String> get(String... params) {
		validate(params);

		String idl = params[0];
		String idp = params[1];
		String ido = params[2];

		return get(idl, idp, ido);
	}

	/**
	 * Invokes the former system, which always returns HTML, so that's why this
	 * method returns a String. It will invoke the former system within a
	 * promise with a timeout of 7500 milliseconds.
	 *
	 * @param idl the routeId of the bus stop
	 * @param idp the id of the bus stop
	 * @param ido the order id of the bus stop
	 *
	 * @return the response of the former system in HTML format
	 */
	private Promise<String> get(String idl, String idp, String ido) {
		String url = MessageFormat.format(ENDPOINT, idl, idp, ido);

		WSRequestHolder wsRequestHolder = WS.url(url);

		Promise<WSResponse> responsePromise = wsRequestHolder.get();

		Promise<String> documentPromise = responsePromise.map(
			response -> {
				byte[] bytes = response.asByteArray();

				return new String(bytes, Charset.forName("UTF-8"));
			}
		);

		return documentPromise;
	}

	private static final String ENDPOINT =
		"http://unauto.twa.es/code/getparadas.php?idl={0}&idp={1}&ido={2}";

}
