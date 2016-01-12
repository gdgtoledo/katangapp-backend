package es.craftsmanship.toledo.katangapp.internal.algorithm;

import es.craftsmanship.toledo.katangapp.business.ClosestPointsAlgorithm;
import es.craftsmanship.toledo.katangapp.business.UnreferenceablePointException;
import es.craftsmanship.toledo.katangapp.models.ReferenceablePoint;
import es.craftsmanship.toledo.katangapp.models.Segment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author mdelapenya
 */
public class KatangappAlgorithm implements ClosestPointsAlgorithm {

	public static final int DEFAULT_MAX_ELEMENTS = 3;

	public List<Segment> closestSegments(
		ReferenceablePoint currentLocation, Set<ReferenceablePoint> points,
		int radiusMeters) {

		return closestSegments(
			currentLocation, points, radiusMeters, DEFAULT_MAX_ELEMENTS);
	}

	protected List<Segment> closestSegments(
		ReferenceablePoint currentLocation, Set<ReferenceablePoint> points,
		int radiusMeters, int maxElements) {

		List<Segment> segments = new ArrayList<>();

		// each segment will internally store the distance between points

		for (ReferenceablePoint point : points) {
			Segment segment;

			try {
				segment = new Segment(currentLocation, point);
			}
			catch (UnreferenceablePointException e) {
				continue;
			}

			if (segment.getDistance() <= radiusMeters) {
				segments.add(segment);
			}
		}

		// fail fast, avoiding sorting

		if (segments.isEmpty()) {
			return segments;
		}

		// sort segments using the distance field

		Collections.sort(segments);

		List<Segment> result = new ArrayList<>();

		for (int i = 0; i < maxElements; i++) {
			result.add(segments.get(i));
		}

		return result;
	}

}
