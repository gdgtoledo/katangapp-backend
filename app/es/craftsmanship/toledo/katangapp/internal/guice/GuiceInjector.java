package es.craftsmanship.toledo.katangapp.internal.guice;

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
		injector = Guice.createInjector(
			new ClosestPointAlgorithmModule(), new FinderModule(),
			new ParserModule());
	}

	private static GuiceInjector instance = new GuiceInjector();

	private Injector injector;

}