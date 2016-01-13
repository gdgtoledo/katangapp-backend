package es.craftsmanship.toledo.katangapp.controllers;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import es.craftsmanship.toledo.katangapp.controllers.callbacks.BodyEqualsTestCallback;

import org.junit.Test;

/**
 * @author mdelapenya
 */
public class KatangappFunctionalTest {

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
