package es.craftsmanship.toledo.katangapp.controllers;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import es.craftsmanship.toledo.katangapp.controllers.callbacks.BodyEqualsTestCallback;
import es.craftsmanship.toledo.katangapp.test.SpecsContants;

import org.junit.Test;

/**
 * @author mdelapenya
 */
public class UnautoFunctionalTest {

    @Test
    public void testNotFoundPath() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyEqualsTestCallback(
                SpecsContants.SERVER_PORT, "/notfound",
                "{\"message\":\"Don't try to hack the URI!\"}")
        );
    }

    @Test
    public void testRootPath() {
        running(
            testServer(
                SpecsContants.SERVER_PORT, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyEqualsTestCallback(
                SpecsContants.SERVER_PORT, "/",
                "{\"message\":\"Don't try to hack the URI!\"}")
        );
    }

}