package es.craftsmanship.toledo.katangapp.internal.store;

import static org.fest.assertions.Assertions.assertThat;

import es.craftsmanship.toledo.katangapp.models.BusStop;

import org.junit.Test;

/**
 * @author mdelapenya
 */
public class BusStopOrderComparatorTest {

	@Test
	public void testOrderEquals() {
		testCompare("12.000", "12.000", 0);
	}

	@Test
	public void testOrderGreatherThan() {
		testCompare("15.000", "12.000", 1);
	}

	@Test
	public void testOrderLessThan() {
		testCompare("5.000", "12.000", -1);
	}

	private void testCompare(String order1, String order2, int expected) {
		BusStopOrderComparator busStopOrderComparator =
			new BusStopOrderComparator();

		BusStop busStop1 = new BusStop();

		busStop1.setOrder(order1);

		BusStop busStop2 = new BusStop();
		busStop2.setOrder(order2);

		assertThat(busStopOrderComparator.compare(busStop1, busStop2))
			.isEqualTo(expected);
	}

}