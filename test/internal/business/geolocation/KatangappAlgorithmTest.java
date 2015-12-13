package internal.business.geolocation;

import static org.fest.assertions.Assertions.assertThat;

import business.mocks.MockBusStopStore;

import models.BusStop;
import models.ReferenceablePoint;
import models.TestPointFactory;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author mdelapenya
 */
public class KatangappAlgorithmTest {

	@Test
	public void testClosest() {
		testGetMaxElementsReturnedByClosestPoints(
			KatangappAlgorithm.DEFAULT_MAX_ELEMENTS, 2000);
	}

	@Test
	public void testClosestShouldNotReturnDefaultMaxElements() {
		testGetMaxElementsReturnedByClosestPoints(4, 2000);
	}

	private void testGetMaxElementsReturnedByClosestPoints(
		int maxElements, int radius) {

		ReferenceablePoint current = TestPointFactory.getPuertaBisagra();

		MockBusStopStore mockBusStopStore = new MockBusStopStore();

		Map<String, BusStop> store = mockBusStopStore.getStore();

		Set<ReferenceablePoint> points = new HashSet<ReferenceablePoint>(store.values());

		KatangappAlgorithm katangappAlgorithm = new KatangappAlgorithm();

		List<ReferenceablePoint> referenceablePoints =
			katangappAlgorithm.closestPoints(
				current, points, radius, maxElements);

		assertThat(referenceablePoints).hasSize(maxElements);
	}

}
