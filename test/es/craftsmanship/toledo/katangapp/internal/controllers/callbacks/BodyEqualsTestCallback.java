package es.craftsmanship.toledo.katangapp.internal.controllers.callbacks;

import static org.fest.assertions.Assertions.assertThat;

import play.test.TestBrowser;

/**
 * @author mdelapenya
 */
public class BodyEqualsTestCallback extends DefaultBodyTestCallback {

	public BodyEqualsTestCallback(String endPoint, String message) {
		super(endPoint, message);
	}

	@Override
	protected void verifyAssertion(TestBrowser browser) {
		String message = (String) payload;

		assertThat(browser.pageSource()).isEqualTo(message);
	}

}
