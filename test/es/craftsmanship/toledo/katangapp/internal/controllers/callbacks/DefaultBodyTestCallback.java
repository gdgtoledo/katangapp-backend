package es.craftsmanship.toledo.katangapp.internal.controllers.callbacks;

import es.craftsmanship.toledo.katangapp.test.SpecsContants;

import play.libs.F;

import play.test.TestBrowser;

/**
 * @author mdelapenya
 */
public abstract class DefaultBodyTestCallback implements F.Callback<TestBrowser> {

	public DefaultBodyTestCallback(String endPoint, String message) {
		this.endPoint = endPoint;
		this.messages = new String[] {message};
	}

	public DefaultBodyTestCallback(String endPoint, String[] messages) {
		this.endPoint = endPoint;
		this.messages = messages;
	}

	@Override
	public void invoke(TestBrowser browser) {
		browser.goTo(
			"http://localhost:" + SpecsContants.SERVER_PORT + endPoint);

		verifyAssertion(browser);
	}

	protected abstract void verifyAssertion(TestBrowser browser);

	protected String endPoint;
	protected String[] messages;

}
