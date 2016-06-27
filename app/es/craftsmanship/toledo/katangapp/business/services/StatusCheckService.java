package es.craftsmanship.toledo.katangapp.business.services;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.F.Promise;

/**
 * @author mdelapenya
 */
public interface StatusCheckService {

	Promise<JsonNode> healthCheck();

}
