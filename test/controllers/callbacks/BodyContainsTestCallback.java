package controllers.callbacks;

import static org.fest.assertions.Assertions.assertThat;

import play.test.TestBrowser;


/**
 * @author Manuel de la Peña
 */
public final class BodyContainsTestCallback extends BodyEqualsTestCallback {

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
