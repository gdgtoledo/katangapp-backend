package es.craftsmanship.toledo.katangapp.internal.algorithm;

import static org.fest.assertions.Assertions.assertThat;

import es.craftsmanship.toledo.katangapp.business.store.Store;
import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;
import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.ReferenceablePoint;
import es.craftsmanship.toledo.katangapp.models.Segment;
import es.craftsmanship.toledo.katangapp.models.TestPointFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class SegmentsAlgorithmTest extends WithApplication {

	@Test
	public void testClosest() {
		testGetMaxElementsReturnedByClosestSegments(
			SegmentsAlgorithm.DEFAULT_MAX_ELEMENTS, 2000);
	}

	@Test
	public void testClosestIsEmpty() {
		testGetMaxElementsReturnedByClosestSegments(0, 2000);
	}

	@Test
	public void testClosestAreLessThanDefault() {
		testGetMaxElementsReturnedByClosestSegments(3, 100);
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

		SegmentsAlgorithm segmentsAlgorithm = new SegmentsAlgorithm();

		List<Segment> referenceablePoints =
			segmentsAlgorithm.closestSegments(
				current, points, radius, maxElements);

		assertThat(referenceablePoints).hasSize(maxElements);
	}

}
