package es.craftsmanship.toledo.katangapp.api.controllers;

import es.craftsmanship.toledo.katangapp.api.Finder;
import es.craftsmanship.toledo.katangapp.api.exception.APIElementNotFoundException;
import es.craftsmanship.toledo.katangapp.api.exception.APIException;
import es.craftsmanship.toledo.katangapp.business.UnreferenceablePointException;
import es.craftsmanship.toledo.katangapp.internal.controllers.BaseKatangaApplication;
import es.craftsmanship.toledo.katangapp.models.QueryResult;

import com.google.inject.Inject;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import play.libs.F;
import play.libs.Json;

import play.mvc.Result;

/**
 * @author manudevelopia
 * @author mdelapenya
 */
@Api(value = "/katanga")
public class KatangappFavoriteApplication extends BaseKatangaApplication {

    @Inject
    public KatangappFavoriteApplication(Finder busStopFinder) {
        this.busStopFinder = busStopFinder;
    }

    @ApiImplicitParams({
        @ApiImplicitParam(
            name = "busStopId",
            dataType = "String",
            defaultValue = "P001",
            required = true,
            paramType = "path",
            value = "Bus stop identifier"
        )
    })
    @ApiOperation(
        httpMethod = "GET",
        notes = "It uses the uses the UNAUTO service to get HTML representing" +
            " the timetable for the routes passing by this bus stop. That" +
            " HTML is parsed using regular expressions and converted into JSON.",
        produces = "application/json",
        value = "Returns the estimated bus routes timetable for this bus stop"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                code = 200, message = "Bus stop found and timetable retrieved.",
                response = Result.class),
            @ApiResponse(
                code = 400, message = "Don't try to hack the URI!",
                response = Result.class),
            @ApiResponse(
                code = 404, message = "Not found",
                response = Result.class),
        }
    )
    public F.Promise<Result> favorite(final String busStopId) {
        try {
            F.Promise<QueryResult> queryResultPromise = 
                busStopFinder.findRoutes(busStopId);

            F.Promise<Result> resultPromise = queryResultPromise.map(
                new F.Function<QueryResult, Result>() {
        
                    @Override
                    public Result apply(QueryResult queryResult)
                        throws Throwable {

                        return prettyPrint(Json.toJson(queryResult));
                    }

                });

            return recover(resultPromise);
        }
        catch (final APIElementNotFoundException ae) {
            return F.Promise.promise(new F.Function0<Result>() {
    
                @Override
                public Result apply() throws Throwable {
                        return notFound(ae.getApiError());
                    }

                });
        }
        catch (InterruptedException | UnreferenceablePointException e) {
            final APIException apiException = new APIException(e.getMessage());

            return F.Promise.promise(new F.Function0<Result>() {
    
                @Override
                public Result apply() throws Throwable {
                    return notFound(apiException.getApiError());
                }

            });
        }

    }

    private Finder busStopFinder;

}
