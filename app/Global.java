import es.craftsmanship.toledo.katangapp.business.exception.APIException;
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