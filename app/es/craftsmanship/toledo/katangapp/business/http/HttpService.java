package es.craftsmanship.toledo.katangapp.business.http;

import play.libs.F.Promise;

/**
 * @author mdelapenya
 */
public interface HttpService {

	int TIMEOUT = 7500;

	Promise<String> get(String... params);

	void validate(String... params);

}