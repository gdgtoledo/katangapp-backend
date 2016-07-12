package es.craftsmanship.toledo.katangapp.internal.controllers.callbacks;

import org.junit.Test;

import play.libs.F;

/**
 * @author mdelapenya
 */
public class BodyCountsMultipleJsonTestCallbackTest {

	@Test(expected = IllegalArgumentException.class)
	public void testVerifyWithDifferentSizedPayloads() {
		F.Tuple tuple = new F.Tuple(new Integer[3], new String[2]);

		new BodyCountsMultipleJsonTestCallback("foo", tuple);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testVerifyWithNullPayloads() {
		F.Tuple tuple = new F.Tuple(null, null);

		new BodyCountsMultipleJsonTestCallback("foo", tuple);
	}

}