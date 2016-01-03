package es.craftsmanship.toledo.katangapp.business;

import static org.fest.assertions.Assertions.assertThat;

import es.craftsmanship.toledo.katangapp.internal.BusStopsFinder;
import es.craftsmanship.toledo.katangapp.mocks.MockHttpService;
import es.craftsmanship.toledo.katangapp.internal.geolocation.KatangappAlgorithm;
import es.craftsmanship.toledo.katangapp.models.BusStopResult;
import es.craftsmanship.toledo.katangapp.models.Point;
import es.craftsmanship.toledo.katangapp.models.QueryResult;
import es.craftsmanship.toledo.katangapp.models.TestPointFactory;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import play.test.WithApplication;

/**
 * @author mdelapenya
 */
public class FinderTest extends WithApplication {

	@Before
	public void setUp() {
		busStopFinder = new BusStopsFinder(new MockHttpService("P001"));
	}

	@Test
	public void testFindRoutes() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();
		int radius = 2000;

		QueryResult queryResult = busStopFinder.findRoutes(
			puertaBisagra.getLatitude(), puertaBisagra.getLongitude(), radius);

		List<BusStopResult> results = queryResult.getResults();

		assertThat(results).hasSize(KatangappAlgorithm.DEFAULT_MAX_ELEMENTS);
	}

	@Test
	public void testFindRoutesWithoutRadiusShouldNotReturnRoutes() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();
		int radius = 0;

		QueryResult queryResult = busStopFinder.findRoutes(
			puertaBisagra.getLatitude(), puertaBisagra.getLongitude(), radius);

		List<BusStopResult> results = queryResult.getResults();

		assertThat(results).hasSize(0);
	}

	private Finder busStopFinder;

}
