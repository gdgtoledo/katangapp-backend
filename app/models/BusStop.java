package models;

import business.UnreferenceablePointException;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author mdelapenya
 */
public class BusStop implements ReferenceablePoint {

	public BusStop() {
	}

	public BusStop(
		String routeId, String id, String order, double latitude,
		double longitude, String address) {

		this.address = address;
		this.routeId = routeId;
		this.order = order;
		this.id = id;
		this.point = new Point(latitude, longitude);
	}

	public double distanceTo(ReferenceablePoint to)
		throws UnreferenceablePointException {

		return point.distanceTo(to);
	}

	public String getAddress() {
		return address;
	}

	public String getRouteId() {
		return routeId;
	}

	public String getOrder() {
		return order;
	}

	public String getId() {
		return id;
	}

	@Override
	public double getLatitude() {
		return getPoint().getLatitude();
	}

	@Override
	public double getLongitude() {
		return getPoint().getLongitude();
	}

	public Point getPoint() {
		return point;
	}

	@Override
	public String toString() {
		return "routeId: " + routeId + ", id: " + id + ", order: " + order +
			", " + point + ", address:" + address;
	}

	public void setCoordinates(double latitude, double longitude) {
		if (point == null) {
			point = new Point(latitude, longitude);
		}
		else {
			point.setLatitude(latitude);
			point.setLongitude(longitude);
		}
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	private String routeId;

	private String order;

	private String id;

	private String address;

	@JsonIgnore
	private Point point;

}
