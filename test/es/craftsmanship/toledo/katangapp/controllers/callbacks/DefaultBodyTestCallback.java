package es.craftsmanship.toledo.katangapp.controllers.callbacks;

import play.libs.F;
import play.test.TestBrowser;

/**
 * @author mdelapenya
 */
public abstract class DefaultBodyTestCallback implements F.Callback<TestBrowser> {

	public DefaultBodyTestCallback(
		int serverPort, String endPoint, String message) {

		this.endPoint = endPoint;
		this.message = message;
		this.serverPort = serverPort;
	}

	@Override
	public void invoke(TestBrowser browser) {
		browser.goTo("http://localhost:" + serverPort + endPoint);

		verifyAssertion(browser);
	}

	protected abstract void verifyAssertion(TestBrowser browser);

	protected String endPoint;
	protected String message;
	protected int serverPort;

}
