package es.craftsmanship.toledo.katangapp.services;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.F.Promise;

/**
 * @author mdelapenya
 */
public interface StatusCheckServiceDiscoveryManager {

	Promise<JsonNode> processServices();

}
