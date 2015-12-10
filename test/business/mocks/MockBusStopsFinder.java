package business.mocks;

import business.Finder;

import internal.business.BusStopsFinder;

import org.mockito.Mockito;

/**
 * @author mdelapenya
 */
public class MockBusStopsFinder {

	public static Finder mockFinder() {
		Finder finder = Mockito.spy(new BusStopsFinder());

		Mockito.when(finder.getStore()).thenReturn(new MockBusStopStore());

		Mockito.when(finder.getHttpService())
			.thenReturn(new MockHttpService("P001"));

		return finder;
	}

}
