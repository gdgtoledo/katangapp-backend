package es.craftsmanship.toledo.katangapp.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import es.craftsmanship.toledo.katangapp.business.UnreferenceablePointException;

/**
 * @author mdelapenya
 */
@JsonPropertyOrder(alphabetic = true)
public class Point implements ReferenceablePoint {

	public Point(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double distanceTo(ReferenceablePoint to)
		throws UnreferenceablePointException {

		if (to == null) {
			throw new UnreferenceablePointException();
		}

		double latitude1 = this.getLatitude();
		double longitude1 = this.getLongitude();

		double latitude2 = to.getLatitude();
		double longitude2 = to.getLongitude();

		double latitudeRadians = Math.toRadians(latitude2 - latitude1);

		double longitudeRadians = Math.toRadians(longitude2 - longitude1);

		double a = Math.sin(latitudeRadians / 2) *
			Math.sin(latitudeRadians / 2) +
			Math.cos(Math.toRadians(latitude1)) *
				Math.cos(Math.toRadians(latitude2)) *
				Math.sin(longitudeRadians / 2) *
				Math.sin(longitudeRadians / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

		return (Constants.EARTH_RADIUS_METERS * c);
	}

	@Override
	public boolean equals(final Object that) {
		if (this == that) {
			return true;
		}

		if (!(that instanceof Point)) {
			return false;
		}

		Point thatPoint = (Point)that;

		return ((this.latitude == thatPoint.latitude) &&
			(this.longitude == thatPoint.longitude));
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public int hashCode() {
		int hashCode = 31;

		long l = Double.doubleToLongBits(latitude);

		int c = (int) (l ^ (l >>> 32));

		hashCode = 37 * hashCode + c;

		l = Double.doubleToLongBits(longitude);

		c = (int) (l ^ (l >>> 32));

		hashCode = 37 * hashCode + c;

		return hashCode;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "lt: " + latitude + ", ln: " + longitude;
	}

	private double latitude;
	private double longitude;

}
