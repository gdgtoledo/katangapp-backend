package controllers.callbacks;

import static org.fest.assertions.Assertions.assertThat;

import play.test.TestBrowser;

/**
 * @author mdelapenya
 */
public class BodyEqualsTestCallback extends DefaultBodyTestCallback {

	public BodyEqualsTestCallback(
		int serverPort, String endPoint, String message) {

		super(serverPort, endPoint, message);
	}

	@Override
	protected void verifyAssertion(TestBrowser browser) {
		assertThat(browser.pageSource()).isEqualTo(message);
	}

}
