import es.craftsmanship.toledo.katangapp.business.exception.APIException;
import es.craftsmanship.toledo.katangapp.guice.GuiceInjector;

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

	public void onStart(Application application) {
		guiceInjector = GuiceInjector.getInstance();
	}

	@Override
	public <A> A getControllerInstance(Class<A> controllerClass)
		throws Exception {

		return guiceInjector.getInjectedInstance(controllerClass);
	}

	private GuiceInjector guiceInjector;

}