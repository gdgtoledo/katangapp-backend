package models;

import business.UnreferenceablePointException;

/**
 * @author mdelapenya
 */
public class PolarSegment implements Comparable<PolarSegment> {

	public PolarSegment(ReferenceablePoint from, ReferenceablePoint to)
		throws UnreferenceablePointException {

		this.from = from;
		this.to = to;
		this.distance = from.distanceTo(to);
	}

	@Override
	public int compareTo(PolarSegment that) {
		if (distance < that.distance) {
			return -1;
		}
		else if (distance > that.getDistance()) {
			return 1;
		}

		return 0;
	}

	@Override
	public boolean equals(final Object that) {
		if (this == that) {
			return true;
		}

		if (!(that instanceof PolarSegment)) {
			return false;
		}

		PolarSegment polarThat = (PolarSegment)that;

		return((distance == polarThat.distance) &&
			(from.equals(polarThat.from)) && (to.equals(polarThat.to)));
	}

	public double getDistance() {
		return distance;
	}

	public ReferenceablePoint getFrom() {
		return from;
	}

	public ReferenceablePoint getTo() {
		return to;
	}

	@Override
	public int hashCode() {
		int hashCode = 31;

		long l = Double.doubleToLongBits(distance);

		int c = (int) (l ^ (l >>> 32));

		hashCode = 37 * hashCode + c;

		c = from.hashCode();

		hashCode = 37 * hashCode + c;

		c = to.hashCode();

		hashCode = 37 * hashCode + c;

		return hashCode;
	}

	@Override
	public String toString() {
		return "from: [" + from + "], to: [" + to + "], distance: " + distance;
	}

	private final double distance;
	private final ReferenceablePoint from;
	private final ReferenceablePoint to;

}
