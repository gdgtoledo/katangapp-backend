package business.mocks;

import business.Finder;

import internal.business.BusStopsFinder;

import org.mockito.Mockito;

/**
 * @author mdelapenya
 */
public class MockBusStopsFinder {

	public static Finder mockFinder(MockHttpService mockHttpService) {
		Finder finder = Mockito.spy(new BusStopsFinder());

		Mockito.when(finder.getHttpService()).thenReturn(mockHttpService);

		return finder;
	}

}
