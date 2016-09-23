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

import es.craftsmanship.toledo.katangapp.api.exception.APIException;
import es.craftsmanship.toledo.katangapp.internal.guice.GuiceInjector;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;

import static play.mvc.Results.*;

/**
 * @author mdelapenya
 */
public class Global extends GlobalSettings {

	public Promise<Result> onBadRequest(RequestHeader request, String error) {
		return Promise.<Result>pure(createBadRequest());
	}

	public Promise<Result> onHandlerNotFound(RequestHeader request) {
		return Promise.<Result>pure(createBadRequest());
	}

	private Results.Status createBadRequest() {
		APIException badRequest = new APIException(
			"Don't try to hack the URI!");

		return badRequest(badRequest.getApiError());
	}

	@Override
	public Action<?> onRequest(
		Http.Request request, java.lang.reflect.Method actionMethod) {

		return new ActionCORSWrapper(super.onRequest(request, actionMethod));
	}

	public void onStart(Application application) {
		guiceInjector = GuiceInjector.getInstance();
	}

	@Override
	public <A> A getControllerInstance(Class<A> controllerClass)
		throws Exception {

		return guiceInjector.getInjectedInstance(controllerClass);
	}

	private GuiceInjector guiceInjector;

	private class ActionCORSWrapper extends Action.Simple {
		public ActionCORSWrapper(Action<?> action) {
			this.delegate = action;
		}

		@Override
		public Promise<Result> call(Http.Context ctx) throws Throwable {
			Promise<Result> result = this.delegate.call(ctx);

			Http.Response response = ctx.response();

			response.setHeader(HeaderNames.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

			return result;
		}
	}

}