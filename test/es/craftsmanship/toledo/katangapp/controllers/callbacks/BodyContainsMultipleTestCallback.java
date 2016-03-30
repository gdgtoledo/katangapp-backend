package es.craftsmanship.toledo.katangapp.controllers.callbacks;

import static org.fest.assertions.Assertions.assertThat;

import play.test.TestBrowser;

/**
 * @author mdelapenya
 */
public final class BodyContainsMultipleTestCallback
	extends DefaultBodyTestCallback {

	public BodyContainsMultipleTestCallback(
		int serverPort, String endPoint, String[] messages) {

		super(serverPort, endPoint, messages);
	}

	@Override
	protected void verifyAssertion(TestBrowser browser) {
		for (String message : messages) {
			assertThat(browser.pageSource()).contains(message);
		}
	}

}
