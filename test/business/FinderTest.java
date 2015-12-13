package business;

import static org.fest.assertions.Assertions.assertThat;

import business.mocks.MockBusStopsFinder;
import business.mocks.MockHttpService;

import internal.business.geolocation.GeoLocator;

import models.BusStopResult;
import models.Point;
import models.QueryResult;
import models.TestPointFactory;

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
		busStopFinder = MockBusStopsFinder.mockFinder(
			new MockHttpService("P001"));
	}

	@Test
	public void testFindRoutes() {
		Point puertaBisagra = TestPointFactory.getPuertaBisagra();
		int radius = 2000;

		QueryResult queryResult = busStopFinder.findRoutes(
			puertaBisagra.getLatitude(), puertaBisagra.getLongitude(), radius);

		List<BusStopResult> results = queryResult.getResults();

		assertThat(results).hasSize(GeoLocator.DEFAULT_MAX_ELEMENTS);
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
