package es.craftsmanship.toledo.katangapp.business;

import es.craftsmanship.toledo.katangapp.business.exception.APIElementNotFoundException;
import es.craftsmanship.toledo.katangapp.models.QueryResult;

import play.libs.F.Promise;

/**
 * @author mdelapenya
 */
public interface Finder {

	Promise<QueryResult> findRoutes(String busStopId)
		throws APIElementNotFoundException, InterruptedException,
			UnreferenceablePointException ;

	Promise<QueryResult> findRoutes(
			double latitude, double longitude, int radius)
		throws InterruptedException;

}