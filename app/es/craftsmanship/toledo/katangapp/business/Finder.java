package es.craftsmanship.toledo.katangapp.business;

import es.craftsmanship.toledo.katangapp.models.QueryResult;

import play.libs.F.Promise;

/**
 * @author mdelapenya
 */
public interface Finder {

	Promise<QueryResult> findRoutes(
			double latitude, double longitude, int radius)
		throws InterruptedException;

}