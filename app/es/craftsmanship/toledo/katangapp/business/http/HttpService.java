package es.craftsmanship.toledo.katangapp.business.http;

/**
 * @author mdelapenya
 */
public interface HttpService {

	String get(String... params);

	void validate(String... params);

}