package es.craftsmanship.toledo.katangapp.controllers;

import com.google.inject.Inject;

import es.craftsmanship.toledo.katangapp.business.http.HttpService;

import play.libs.F.Function;
import play.libs.F.Promise;

import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class UnautoApplication extends BaseKatangaApplication {

    @Inject
    public UnautoApplication(HttpService httpService) {
        this.httpService = httpService;
    }

    public Promise<Result> unauto(String idl, String idp, String ido) {
        Promise<String> httpPromise = httpService.get(idl, idp, ido);

        Promise<Result> promiseOfResult = httpPromise.map(
            new Function<String, Result>() {

                public Result apply(String result) {
                    return ok(result);
                }

            }
        );

        return recover(promiseOfResult);
    }

    private HttpService httpService;

}