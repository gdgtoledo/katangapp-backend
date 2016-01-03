package es.craftsmanship.toledo.katangapp.controllers;

import com.google.inject.Inject;

import es.craftsmanship.toledo.katangapp.business.http.HttpService;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author mdelapenya
 */
public class UnautoApplication extends Controller {

    @Inject
    public UnautoApplication(HttpService httpService) {
        this.httpService = httpService;
    }

    public Result unauto(String idl, String idp, String ido) {
        String response = httpService.get(idl, idp, ido);

        return ok(response);
    }

    private HttpService httpService;

}