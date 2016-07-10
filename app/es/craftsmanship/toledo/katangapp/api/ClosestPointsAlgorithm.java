package es.craftsmanship.toledo.katangapp.api;

import es.craftsmanship.toledo.katangapp.models.ReferenceablePoint;
import es.craftsmanship.toledo.katangapp.models.Segment;

import java.util.List;
import java.util.Set;

/**
 * @author mdelapenya
 */
public interface ClosestPointsAlgorithm {

	List<Segment> closestSegments(
		ReferenceablePoint currentLocation, Set<ReferenceablePoint> points,
		int radiusMeters);

}
