package internal.business.geolocation;

import static org.fest.assertions.Assertions.assertThat;

import business.store.Store;

import internal.business.store.KatangappStore;

import models.BusStop;
import models.PolarSegment;
import models.ReferenceablePoint;
import models.TestPointFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class KatangappAlgorithmTest extends WithApplication {

	@Test
	public void testClosest() {
		testGetMaxElementsReturnedByClosestSegments(
			KatangappAlgorithm.DEFAULT_MAX_ELEMENTS, 2000);
	}

	@Test
	public void testClosestShouldNotReturnDefaultMaxElements() {
		testGetMaxElementsReturnedByClosestSegments(4, 2000);
	}

	private void testGetMaxElementsReturnedByClosestSegments(
		int maxElements, int radius) {

		ReferenceablePoint current = TestPointFactory.getPuertaBisagra();

		Store katangappStore = KatangappStore.getInstance();

		Map<String, BusStop> store = katangappStore.getBusStopStore();

		Set<ReferenceablePoint> points = new HashSet<ReferenceablePoint>(
			store.values());

		KatangappAlgorithm katangappAlgorithm = new KatangappAlgorithm();

		List<PolarSegment> referenceablePoints =
			katangappAlgorithm.closestSegments(
				current, points, radius, maxElements);

		assertThat(referenceablePoints).hasSize(maxElements);
	}

}
