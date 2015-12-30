package es.craftsmanship.toledo.katangapp.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author mdelapenya
 */
public class BusStopResult {

	public BusStopResult(
		double distance, BusStop busStop, List<RouteResult> results) {

		this.busStop = busStop;
		this.distance = distance;
		this.results = results;
	}

	public BusStop getBusStop() {
		return busStop;
	}

	public double getDistance() {
		return distance;
	}

	public List<RouteResult> getResults() {
		return results;
	}

	@JsonProperty("distancia")
	private double distance;

	@JsonProperty("parada")
	private BusStop busStop;

	@JsonProperty("tiempos")
	private List<RouteResult> results;

}
