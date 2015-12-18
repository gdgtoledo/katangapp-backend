package controllers.callbacks;

import static org.fest.assertions.Assertions.assertThat;

import play.libs.F;
import play.test.TestBrowser;

/**
 * @author mdelapenya
 */
public class BodyEqualsTestCallback implements F.Callback<TestBrowser> {

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
