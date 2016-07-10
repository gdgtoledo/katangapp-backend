package es.craftsmanship.toledo.katangapp.internal.guice;

import es.craftsmanship.toledo.katangapp.api.parser.Parser;
import es.craftsmanship.toledo.katangapp.internal.parser.HTMLParser;

import com.google.inject.AbstractModule;

/**
 * @author mdelapenya
 */
public class ParserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Parser.class).to(HTMLParser.class);
	}

}