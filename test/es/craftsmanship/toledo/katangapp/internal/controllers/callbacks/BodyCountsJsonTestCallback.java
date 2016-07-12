package es.craftsmanship.toledo.katangapp.internal.controllers.callbacks;

import static org.fest.assertions.Assertions.assertThat;

import java.util.regex.Pattern;

import play.libs.F;

import play.test.TestBrowser;

/**
 * @author mdelapenya
 */
public class BodyCountsJsonTestCallback extends DefaultBodyTestCallback {

	public BodyCountsJsonTestCallback(
		String endPoint, F.Tuple tuple) {

		super(endPoint, tuple);
	}

	@Override
	protected void verifyAssertion(TestBrowser browser) {
		F.Tuple tuple = (F.Tuple) this.payload;

		Integer count = (Integer) tuple._1;
		String key = (String) tuple._2;

		String pageSource = browser.pageSource();

		assertThat(countTokens(key, pageSource)).isEqualTo(count);
	}

	/**
	 * The result of split() will contain one more element than the delimiter.
	 * The "-1" second argument makes it not discard trailing empty strings
	 */
	public static int countTokens(String token, String string){
		return string.split(Pattern.quote(token), -1).length - 1;
	}

}
