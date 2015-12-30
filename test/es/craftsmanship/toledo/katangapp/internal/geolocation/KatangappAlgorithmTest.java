package es.craftsmanship.toledo.katangapp.internal.geolocation;

import static org.fest.assertions.Assertions.assertThat;

import es.craftsmanship.toledo.katangapp.business.store.Store;

import es.craftsmanship.toledo.katangapp.internal.store.KatangappStore;

import es.craftsmanship.toledo.katangapp.models.BusStop;
import es.craftsmanship.toledo.katangapp.models.PolarSegment;
import es.craftsmanship.toledo.katangapp.models.ReferenceablePoint;
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
