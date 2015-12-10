package business;

import business.http.HttpService;
import business.store.Store;

import models.QueryResult;

/**
 * @author mdelapenya
 */
public interface Finder {

	QueryResult findRoutes(double latitude, double longitude, int radius);

	ClosestPointsAlgorithm getAlgorithm();

	HttpService getHttpService();

	Store getStore();

}
