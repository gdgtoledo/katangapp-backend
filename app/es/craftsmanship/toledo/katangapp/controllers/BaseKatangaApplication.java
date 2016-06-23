package es.craftsmanship.toledo.katangapp.controllers;

import es.craftsmanship.toledo.katangapp.business.exception.APIException;

import play.libs.F;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public abstract class BaseKatangaApplication extends Controller {

	protected F.Promise<Result> recover(F.Promise<Result> promise) {
		return promise.recover(new F.Function<Throwable, Result>() {

			@Override
			public Result apply(Throwable throwable) throws Throwable {
				final APIException apiException = new APIException(
					throwable.getMessage());

				return badRequest(apiException.getApiError());
			}

		});
	}
}