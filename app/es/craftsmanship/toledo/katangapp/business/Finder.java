package es.craftsmanship.toledo.katangapp.business;

import es.craftsmanship.toledo.katangapp.models.QueryResult;

/**
 * @author mdelapenya
 */
public interface Finder {

	QueryResult findRoutes(double latitude, double longitude, int radius);

}