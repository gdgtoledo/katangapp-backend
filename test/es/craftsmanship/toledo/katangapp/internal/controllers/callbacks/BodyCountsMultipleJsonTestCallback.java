package es.craftsmanship.toledo.katangapp.internal.controllers.callbacks;

import static org.fest.assertions.Assertions.assertThat;

import play.libs.F;

import play.test.TestBrowser;

/**
 * @author mdelapenya
 */
public final class BodyCountsMultipleJsonTestCallback
	extends BodyCountsJsonTestCallback {

	public BodyCountsMultipleJsonTestCallback(
		String endPoint, F.Tuple tuple) {

		super(endPoint, tuple);

		Integer[] counts = (Integer[]) tuple._1;
		String[] keys = (String[]) tuple._2;

		if (counts == null || keys == null) {
			throw new IllegalArgumentException(
				"Arrays length must not be null");
		}

		if (counts.length != keys.length) {
			throw new IllegalArgumentException("Arrays length must be equal");
		}
	}

	@Override
	protected void verifyAssertion(TestBrowser browser) {
		F.Tuple tuple = (F.Tuple) this.payload;

		Integer[] counts = (Integer[]) tuple._1;
		String[] keys = (String[]) tuple._2;

		String pageSource = browser.pageSource();

		for (int i = 0; i < counts.length; i++) {
			Integer count = counts[i];
			String key = keys[i];

			assertThat(countTokens(key, pageSource)).isEqualTo(count);
		}
	}

}
