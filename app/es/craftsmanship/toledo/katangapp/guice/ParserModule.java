package es.craftsmanship.toledo.katangapp.guice;

import com.google.inject.AbstractModule;

import es.craftsmanship.toledo.katangapp.business.parser.Parser;
import es.craftsmanship.toledo.katangapp.internal.parser.HTMLParser;

/**
 * @author mdelapenya
 */
public class ParserModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Parser.class).to(HTMLParser.class);
	}

}