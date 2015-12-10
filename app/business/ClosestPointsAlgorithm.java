package business;

import models.ReferenceablePoint;

import java.util.List;
import java.util.Set;

/**
 * @author mdelapenya
 */
public interface ClosestPointsAlgorithm {

	List<ReferenceablePoint> closestPoints(
		ReferenceablePoint currentLocation, Set<ReferenceablePoint> points,
		int radiusMeters);

}