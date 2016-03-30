package es.craftsmanship.toledo.katangapp.controllers;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import es.craftsmanship.toledo.katangapp.controllers.callbacks.BodyContainsMultipleTestCallback;
import es.craftsmanship.toledo.katangapp.controllers.callbacks.BodyEqualsTestCallback;
import es.craftsmanship.toledo.katangapp.models.Point;
import es.craftsmanship.toledo.katangapp.models.TestPointFactory;

import org.junit.Test;

/**
 * @author mdelapenya
 */
public class KatangappFunctionalTest {

    @Test
    public void testMainEndPoint() {
        int serverPort = 3333;

        Point puertaBisagra = TestPointFactory.getPuertaBisagra();

        String url =
            "/paradas?lt=" + puertaBisagra.getLatitude() + "&ln=" +
                puertaBisagra.getLongitude() + "&r=1000";

        String[] expectedMessages = {
            "distancia", "parada", "latitude", "longitude"};

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsMultipleTestCallback(
                serverPort, url, expectedMessages)
        );
    }

    @Test
    public void testNotFoundPath() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyEqualsTestCallback(
                serverPort, "/notfound",
                "{\"message\":\"Don't try to hack the URI!\"}")
        );
    }

    @Test
    public void testRootPath() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyEqualsTestCallback(
                serverPort, "/", "{\"message\":\"Don't try to hack the URI!\"}")
        );
    }

}
