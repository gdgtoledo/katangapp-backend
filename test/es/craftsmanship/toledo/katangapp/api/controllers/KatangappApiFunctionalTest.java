package es.craftsmanship.toledo.katangapp.api.controllers;

import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import es.craftsmanship.toledo.katangapp.internal.controllers.callbacks.BodyContainsTestCallback;
import es.craftsmanship.toledo.katangapp.internal.controllers.callbacks.BodyCountsJsonTestCallback;
import es.craftsmanship.toledo.katangapp.internal.controllers.callbacks.BodyCountsMultipleJsonTestCallback;
import es.craftsmanship.toledo.katangapp.test.SpecsContants;

import org.junit.Test;

import play.libs.F;

/**
 * @author mdelapenya
 */
public class KatangappApiFunctionalTest {

    @Test
    public void testBusStopById() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                "/api/busStops/P001", SpecsContants.BUS_STOP_JSON)
        );
    }

    @Test
    public void testBusStopByIdPrettified() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                "/api/busStops/P001?prettyPrint=1",
                SpecsContants.BUS_STOP_PRETTIFIED_JSON)
        );
    }

    @Test
    public void testBusStopByIdNotFound() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                "/api/busStops/notfound", "\"message\":\"Not Found\"")
        );
    }

    @Test
    public void testBusStops() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyCountsJsonTestCallback(
                "/api/busStops", new F.Tuple(132, "address"))
        );
    }

    @Test
    public void testNotFound() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                "/api/notfound", "\"message\":\"Don't try to hack the URI!\"")
        );
    }

    @Test
    public void testRouteById() {
        Integer[] counts = new Integer[] {1, 1, 1, 34};
        String[] keys = new String[] {
            "\"id\":\"L02\"", "name", "\"busStops\"", "address"
        };

        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyCountsMultipleJsonTestCallback(
                "/api/routes/L02", new F.Tuple(counts, keys))
        );
    }

    @Test
    public void testRouteByIdNotFound() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                "/api/routes/notfound", "\"message\":\"Not Found\"")
        );
    }

    @Test
    public void testRoutes() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyCountsJsonTestCallback(
                "/api/routes", new F.Tuple(23, "\"busStops\""))
        );
    }

}
