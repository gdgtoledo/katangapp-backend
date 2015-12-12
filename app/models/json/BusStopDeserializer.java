package models.json;

import models.BusStop;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author mdelapenya
 */
public class BusStopDeserializer extends JsonDeserializer<BusStop> {

	@Override
	public BusStop deserialize(
			JsonParser jsonParser,
			DeserializationContext deserializationContext)
		throws IOException {

		JsonNode node = jsonParser.getCodec().readTree(jsonParser);

		String id = node.get("id").asText();
		String address = node.get("address").asText();
		double latitude = node.get("lat").asDouble();
		double longitude = node.get("long").asDouble();

		// route data are not filled in here

		return new BusStop(null, id, null, latitude, longitude, address);
	}

}
