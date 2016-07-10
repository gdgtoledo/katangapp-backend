package es.craftsmanship.toledo.katangapp.api.controllers;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import es.craftsmanship.toledo.katangapp.internal.controllers.callbacks.BodyContainsMultipleTestCallback;
import es.craftsmanship.toledo.katangapp.internal.controllers.callbacks.BodyEqualsTestCallback;
import es.craftsmanship.toledo.katangapp.models.Point;
import es.craftsmanship.toledo.katangapp.models.TestPointFactory;
import es.craftsmanship.toledo.katangapp.test.SpecsContants;

import org.junit.Test;

/**
 * @author mdelapenya
 */
public class KatangappFunctionalTest {

    @Test
    public void testMainEndPoint() {
        Point puertaBisagra = TestPointFactory.getPuertaBisagra();

        String url =
            "/main?lt=" + puertaBisagra.getLatitude() + "&ln=" +
                puertaBisagra.getLongitude() + "&r=1000";

        String[] expectedMessages = {
            "distancia", "parada", "latitude", "longitude"};

        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsMultipleTestCallback(url, expectedMessages)
        );
    }

    @Test
    public void testNotFoundPath() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyEqualsTestCallback(
                "/notfound", "{\"message\":\"Don't try to hack the URI!\"}")
        );
    }

    @Test
    public void testRootPath() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyEqualsTestCallback(
                "/", "{\"message\":\"Don't try to hack the URI!\"}")
        );
    }

}