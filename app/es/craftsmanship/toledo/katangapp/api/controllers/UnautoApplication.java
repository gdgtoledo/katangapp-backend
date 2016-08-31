package es.craftsmanship.toledo.katangapp.api.controllers;

import es.craftsmanship.toledo.katangapp.api.http.HttpService;
import es.craftsmanship.toledo.katangapp.internal.controllers.BaseKatangaApplication;

import com.google.inject.Inject;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import play.libs.F.Function;
import play.libs.F.Promise;

import play.mvc.Result;

/**
 * @author mdelapenya
 */
@Api(
    value = "/unauto",
    description = "Backwards-compatible endpoint, which will return the" +
        " original UNAUTO service result for a bus stops in specific route" +
        " and order, all passed as arguments"
)
public class UnautoApplication extends BaseKatangaApplication {

    @Inject
    public UnautoApplication(HttpService httpService) {
        this.httpService = httpService;
    }

    @ApiImplicitParams({
        @ApiImplicitParam(
            name = "idl",
            dataType = "String",
            defaultValue = "L01",
            required = false,
            paramType = "query",
            value = "Bus route identifier"
        ),
        @ApiImplicitParam(
            name = "idp",
            dataType = "String",
            defaultValue = "P001",
            required = false,
            paramType = "query",
            value = "Bus stop identifier"
        ),
        @ApiImplicitParam(
            name = "ido",
            defaultValue = "1.000",
            dataType = "String",
            required = false,
            paramType = "query",
            value = "Order of the bus stop in the bus route"
        )
    })
    @ApiOperation(
        httpMethod = "GET",
        notes = "It uses the UNAUTO service to get HTML representing the" +
            " timetable for the routes passing by that bus stop.",
        produces = "text/plain",
        response = String.class,
        value = "Returns the HTML representing the estimated timetable for a" +
            " bus stop in a route and with an order."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                code = 200,
                message = "UNAUTO service retrieves data for the input arguments",
                response = Result.class)
        }
    )
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