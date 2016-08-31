package es.craftsmanship.toledo.katangapp.api.controllers;

import es.craftsmanship.toledo.katangapp.api.Finder;
import es.craftsmanship.toledo.katangapp.api.exception.APIException;
import es.craftsmanship.toledo.katangapp.internal.controllers.BaseKatangaApplication;
import es.craftsmanship.toledo.katangapp.models.QueryResult;

import com.google.inject.Inject;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import play.libs.F.Function;
import play.libs.F.Function0;
import play.libs.F.Promise;
import play.libs.Json;

import play.mvc.Result;

/**
 * @author mdelapenya
 */
@Api(
    value = "/katanga",
    description = "Main endpoints of the application, which will return the " +
        "estimated bus routes timetable for the closest bus stops to a " +
        "location within a radius, all passed as arguments"
)
public class KatangappApplication extends BaseKatangaApplication {

    @Inject
    public KatangappApplication(Finder busStopFinder) {
        this.busStopFinder = busStopFinder;
    }

    @ApiImplicitParams({
        @ApiImplicitParam(
            name = "lt",
            dataType = "String",
            defaultValue = "39.862658",
            required = false,
            paramType = "query",
            value = "Latitude of current location"
        ),
        @ApiImplicitParam(
            name = "ln",
            dataType = "String",
            defaultValue = "-4.025088",
            required = false,
            paramType = "query",
            value = "Longitude of current location"
        ),
        @ApiImplicitParam(
            name = "r",
            dataType = "String",
            defaultValue = "1000",
            required = false,
            paramType = "query",
            value = "Radius to perform the bus stop search"
        )
    })
    @ApiOperation(
        httpMethod = "GET",
        notes = "It uses the closest point algorithm (based on Earth Radius)," +
            " to get the points representing the bus stops stored in the" +
            " database, and for each bus stops, uses the UNAUTO service to" +
            " get HTML representing the timetable for the routes passing by" +
            " that bus stop. That HTML is parsed using regular expressions and" +
            " converted into JSON.",
        produces = "application/json",
        response = QueryResult.class,
        value = "Returns the routes timetable for the bus stops that are" +
            " closer than radius to current location"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                code = 200, message = "Bus stops present.",
                response = Result.class),
            @ApiResponse(
                code = 400, message = "Don't try to hack the URI!",
                response = Result.class),
        }
    )
    public Promise<Result> main(
        final String lt, final String ln, final int r) {
        
        final double dLatitude = Double.parseDouble(lt);
        final double dLongitude = Double.parseDouble(ln);

        try {
            Promise<QueryResult> queryResultPromise = busStopFinder.findRoutes(
                dLatitude, dLongitude, r);

            Promise<Result> resultPromise = queryResultPromise.map(
                new Function<QueryResult, Result>() {

                    @Override
                    public Result apply(QueryResult queryResult)
                        throws Throwable {

                        return prettyPrint(Json.toJson(queryResult));
                    }
                });

            return recover(resultPromise);
        }
        catch (InterruptedException ie) {
            final APIException apiException = new APIException(ie.getMessage());

            return Promise.promise(new Function0<Result>() {

                @Override
                public Result apply() throws Throwable {
                    return notFound(apiException.getApiError());
                }

            });
        }
    }

    private Finder busStopFinder;

}