package controllers;

import static org.fest.assertions.Assertions.assertThat;

import static play.test.Helpers.HTMLUNIT;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

import org.junit.Test;

import play.libs.F.Callback;

import play.test.TestBrowser;

/**
 * @author mdelapenya
 */
public class KatangappFunctionalTest {

    @Test
    public void testBusStopById() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                serverPort,
                "/api/busStops/P003", "\"id\":\"P003\",\"address\":")
        );
    }

    @Test
    public void testBusStopByIdNotFound() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                serverPort,
                "/api/busStops/notfound", "\"message\":\"Not Found\"")
        );
    }

    @Test
    public void testBusStops() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                serverPort, "/api/busStops", "{\"busStops\":[")
        );
    }

    @Test
    public void testNotFoundPath() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyEqualsTestCallback(
                serverPort, "/notfound", "Don't try to hack the URI!")
        );
    }

    @Test
    public void testRootPath() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyEqualsTestCallback(
                serverPort, "/", "Don't try to hack the URI!")
        );
    }

    @Test
    public void testRouteById() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                serverPort, "/api/routes/L02", "\"routeId\":\"L02\"")
        );
    }

    @Test
    public void testRouteByIdNotFound() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                serverPort,
                "/api/routes/notfound", "\"message\":\"Not Found\"")
        );
    }

    @Test
    public void testRoutes() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new BodyContainsTestCallback(
                serverPort, "/api/routes", "{\"routes\":[")
        );
    }

    private static class BodyEqualsTestCallback
        implements Callback<TestBrowser> {

        public BodyEqualsTestCallback(
            int serverPort, String endPoint, String message) {

            this.endPoint = endPoint;
            this.message = message;
            this.serverPort = serverPort;
        }

        @Override
        public void invoke(TestBrowser browser) {
            browser.goTo("http://localhost:" + serverPort + endPoint);

            assertThat(browser.pageSource()).isEqualTo(message);
        }

        protected String endPoint;
        protected String message;
        protected int serverPort;
    }

    private static final class BodyContainsTestCallback
        extends BodyEqualsTestCallback {

        public BodyContainsTestCallback(
            int serverPort, String endPoint, String message) {

            super(serverPort, endPoint, message);
        }

        @Override
        public void invoke(TestBrowser browser) {
            browser.goTo("http://localhost:" + serverPort + endPoint);

            assertThat(browser.pageSource()).contains(message);
        }
    }

}
