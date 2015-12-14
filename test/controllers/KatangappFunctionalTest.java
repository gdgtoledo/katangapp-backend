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
    public void testRootPath() {
        int serverPort = 3333;

        running(
            testServer(serverPort, fakeApplication(inMemoryDatabase())),
            HTMLUNIT,
            new FunctionalTestCallback(serverPort, "Don't try to hack the URI!")
        );
    }

    private static final class FunctionalTestCallback
        implements Callback<TestBrowser> {

        public FunctionalTestCallback(int serverPort, String message) {
            this.serverPort = serverPort;
            this.message = message;
        }

        @Override
        public void invoke(TestBrowser browser) {
            browser.goTo("http://localhost:" + serverPort);

            assertThat(browser.pageSource()).isEqualTo(message);
        }

        private int serverPort;
        private String message;
    }

}
