package internal.business.geolocation;

import static org.fest.assertions.Assertions.assertThat;

import business.store.Store;

import internal.business.store.BusStopStore;

import models.BusStop;
import models.ReferenceablePoint;
import models.TestPointFactory;

import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class KatangappAlgorithmTest extends WithApplication {

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

		Store busStopStore = BusStopStore.getInstance();

		Map<String, BusStop> store = busStopStore.getStore();

		Set<ReferenceablePoint> points = new HashSet<ReferenceablePoint>(store.values());

		KatangappAlgorithm katangappAlgorithm = new KatangappAlgorithm();

		List<ReferenceablePoint> referenceablePoints =
			katangappAlgorithm.closestPoints(
				current, points, radius, maxElements);

		assertThat(referenceablePoints).hasSize(maxElements);
	}

}
