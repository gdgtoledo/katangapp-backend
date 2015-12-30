package es.craftsmanship.toledo.katangapp.business;

import es.craftsmanship.toledo.katangapp.models.PolarSegment;
import es.craftsmanship.toledo.katangapp.models.ReferenceablePoint;

import java.util.List;
import java.util.Set;

/**
 * @author mdelapenya
 */
public interface ClosestPointsAlgorithm {

	List<PolarSegment> closestSegments(
		ReferenceablePoint currentLocation, Set<ReferenceablePoint> points,
		int radiusMeters);

}
