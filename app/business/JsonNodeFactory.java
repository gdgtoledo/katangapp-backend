package business;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import play.libs.Json;

/**
 * @author mdelapenya
 */
public class JsonNodeFactory {

	public static JsonNode getTextNode(String label, String text) {
		return Json.newObject().set(label, new TextNode(text));
	}

}