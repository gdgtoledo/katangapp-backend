package es.craftsmanship.toledo.katangapp.api.controllers;

import es.craftsmanship.toledo.katangapp.api.exception.APIElementNotFoundException;
import es.craftsmanship.toledo.katangapp.api.store.Store;
import es.craftsmanship.toledo.katangapp.internal.controllers.BaseKatangaApplication;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.Route;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import java.util.Collection;
import java.util.Map;

import play.libs.Json;

import play.mvc.Result;

/**
 * @author mdelapenya
 */
@Api(
    value = "/api",
    description = "API endpoints of the application, which will return the" +
        " plain JSON objects stored in the database"
)
public class KatangappApiApplication extends BaseKatangaApplication {

    @ApiImplicitParams({
        @ApiImplicitParam(
            name = "id",
            dataType = "String",
            defaultValue = "P001",
            required = true,
            paramType = "path",
            value = "Bus stop identifier"
        )
    })
    @ApiOperation(
        httpMethod = "GET",
        notes = "Returns the information stored for the bus stop identified" +
            " by the id path argument.",
        produces = "application/json",
        value = "Returns the bus stop information"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                code = 200, message = "Bus stop found.",
                response = Result.class),
            @ApiResponse(
                code = 400, message = "Don't try to hack the URI!",
                response = Result.class),
            @ApiResponse(
                code = 404, message = "Not found",
                response = Result.class),
        }
    )
    public Result busStop(String id) {
        try {
            BusStop busStop = store.getBusStop(id);

            return prettyPrint(Json.toJson(busStop));
        }
        catch (APIElementNotFoundException e) {
            return notFound(e.getApiError());
        }
    }

    @ApiOperation(
        httpMethod = "GET",
        notes = "Returns the information stored for all bus stops.",
        produces = "application/json",
        value = "Returns all bus stops information"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                code = 200, message = "Bus stops found.",
                response = Result.class),
            @ApiResponse(
                code = 400, message = "Don't try to hack the URI!",
                response = Result.class)
        }
    )
    public Result busStops() {
        Map<String, BusStop> busStops = store.getBusStopStore();

        Collection<BusStop> values = busStops.values();

        return prettyPrint(Json.toJson(values.toArray()));
    }

    @ApiImplicitParams({
        @ApiImplicitParam(
            name = "id",
            dataType = "String",
            defaultValue = "L01",
            required = true,
            paramType = "path",
            value = "Route identifier"
        )
    })
    @ApiOperation(
        httpMethod = "GET",
        notes = "Returns the information stored for the route identified" +
            " by the id path argument.",
        produces = "application/json",
        value = "Returns the route information"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                code = 200, message = "Route found.",
                response = Result.class),
            @ApiResponse(
                code = 400, message = "Don't try to hack the URI!",
                response = Result.class),
            @ApiResponse(
                code = 404, message = "Not found",
                response = Result.class),
        }
    )
    public Result route(String id) {
        try {
            Route route = store.getRoute(id);

            return prettyPrint(Json.toJson(route));
        }
        catch (APIElementNotFoundException e){
            return notFound(e.getApiError());
        }
    }

    @ApiOperation(
        httpMethod = "GET",
        notes = "Returns the information stored for all bus routes.",
        produces = "application/json",
        value = "Returns all bus routes information"
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                code = 200, message = "Routes found.",
                response = Result.class),
            @ApiResponse(
                code = 400, message = "Don't try to hack the URI!",
                response = Result.class)
        }
    )
    public Result routes() {
        Map<String, Route> routes = store.getRouteStore();

        Collection<Route> values = routes.values();

        return prettyPrint(Json.toJson(values.toArray()));
    }

    private static Store store = KatangappStore.getInstance();

}
