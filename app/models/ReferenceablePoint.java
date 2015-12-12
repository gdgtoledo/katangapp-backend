package models;

import business.UnreferenceablePointException;

/**
 * @author mdelapenya
 */
public interface ReferenceablePoint {

	double distanceTo(ReferenceablePoint point)
		throws UnreferenceablePointException;

	double getLatitude();

	double getLongitude();

}
