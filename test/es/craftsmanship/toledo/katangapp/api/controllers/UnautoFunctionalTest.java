package es.craftsmanship.toledo.katangapp.api.controllers;

import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import es.craftsmanship.toledo.katangapp.internal.controllers.callbacks.BodyEqualsTestCallback;
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