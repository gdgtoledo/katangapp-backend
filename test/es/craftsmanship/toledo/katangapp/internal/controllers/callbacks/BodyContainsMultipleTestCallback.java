package es.craftsmanship.toledo.katangapp.internal.controllers.callbacks;

import static org.fest.assertions.Assertions.assertThat;

import play.test.TestBrowser;

/**
 * @author mdelapenya
 */
public final class BodyContainsMultipleTestCallback
	extends DefaultBodyTestCallback {

	public BodyContainsMultipleTestCallback(
		String endPoint, String[] messages) {

		super(endPoint, messages);
	}

	@Override
	protected void verifyAssertion(TestBrowser browser) {
		for (String message : messages) {
			assertThat(browser.pageSource()).contains(message);
		}
	}

}
