package es.craftsmanship.toledo.katangapp.internal.guice;

import es.craftsmanship.toledo.katangapp.business.Finder;
import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.internal.BusStopsFinder;
import es.craftsmanship.toledo.katangapp.internal.http.UnautoHttpService;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author mdelapenya
 */
public class FinderModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Finder.class).to(BusStopsFinder.class);
	}

	@Provides
	HttpService provideHttpService() {
		HttpService httpService = new UnautoHttpService();

		return httpService;
	}

}