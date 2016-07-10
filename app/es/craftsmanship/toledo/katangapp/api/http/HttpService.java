package es.craftsmanship.toledo.katangapp.api.http;

import play.libs.F.Promise;

/**
 * @author mdelapenya
 */
public interface HttpService {

	Promise<String> get(String... params);

	void validate(String... params);

}