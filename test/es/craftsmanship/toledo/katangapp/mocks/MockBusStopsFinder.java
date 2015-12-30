package es.craftsmanship.toledo.katangapp.mocks;

import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.internal.BusStopsFinder;

import org.mockito.Mockito;

/**
 * This class offers methods to mock the original Finder service, overriding its
 * httpService with a mock implementation.
 *
 * @author mdelapenya
 *
 * @see Finder
 * @see MockHttpService
 */
public class MockBusStopsFinder {

	/**
	 * This method spies the Finder service, returning a mock implementation of
	 * the Finder's HTTP service.
	 *
	 * @param mockHttpService the mock implementation of the HTTP service.
	 *
	 * @return A Finder implementation with a mocked HTTP service.
	 * @see Finder
	 */
	public static Finder mockFinder(MockHttpService mockHttpService) {
		Finder finder = Mockito.spy(new BusStopsFinder());

		Mockito.when(finder.getHttpService()).thenReturn(mockHttpService);

		return finder;
	}

}
