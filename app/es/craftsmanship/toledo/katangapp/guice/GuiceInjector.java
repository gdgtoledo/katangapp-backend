package es.craftsmanship.toledo.katangapp.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author mdelapenya
 */
public class GuiceInjector {

	public <T> T getInjectedInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

	public static GuiceInjector getInstance() {
		return instance;
	}

	private GuiceInjector() {
		injector = Guice.createInjector(new FinderModule());
	}

	private static GuiceInjector instance = new GuiceInjector();

	private Injector injector;

}