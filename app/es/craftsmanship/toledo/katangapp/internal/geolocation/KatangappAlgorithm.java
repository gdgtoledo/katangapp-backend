package es.craftsmanship.toledo.katangapp.internal.geolocation;

import es.craftsmanship.toledo.katangapp.business.ClosestPointsAlgorithm;
import es.craftsmanship.toledo.katangapp.business.UnreferenceablePointException;
import es.craftsmanship.toledo.katangapp.models.ReferenceablePoint;
import es.craftsmanship.toledo.katangapp.models.PolarSegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author mdelapenya
 */
public class KatangappAlgorithm implements ClosestPointsAlgorithm {

	public static final int DEFAULT_MAX_ELEMENTS = 3;

	public List<PolarSegment> closestSegments(
		ReferenceablePoint currentLocation, Set<ReferenceablePoint> points,
		int radiusMeters) {

		return closestSegments(
			currentLocation, points, radiusMeters, DEFAULT_MAX_ELEMENTS);
	}

	protected List<PolarSegment> closestSegments(
		ReferenceablePoint currentLocation, Set<ReferenceablePoint> points,
		int radiusMeters, int maxElements) {

		List<PolarSegment> polarSegments = new ArrayList<>();

		// each polar segment will internally store the distance between points

		for (ReferenceablePoint point : points) {
			PolarSegment polarSegment;

			try {
				polarSegment = new PolarSegment(currentLocation, point);
			}
			catch (UnreferenceablePointException e) {
				continue;
			}

			if (polarSegment.getDistance() <= radiusMeters) {
				polarSegments.add(polarSegment);
			}
		}

		// fail fast, avoiding sorting

		if (polarSegments.isEmpty()) {
			return polarSegments;
		}

		// sort polar segments using the distance field

		Collections.sort(polarSegments);

		List<PolarSegment> result = new ArrayList<>();

		for (int i = 0; i < maxElements; i++) {
			result.add(polarSegments.get(i));
		}

		return result;
	}

}
