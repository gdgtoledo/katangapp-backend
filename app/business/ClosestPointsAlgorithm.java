package business;

import models.PolarSegment;
import models.ReferenceablePoint;

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
