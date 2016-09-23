/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.craftsmanship.toledo.katangapp.internal.algorithm;

import es.craftsmanship.toledo.katangapp.api.ClosestPointsAlgorithm;
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
public class SegmentsAlgorithm implements ClosestPointsAlgorithm {

	public static final int DEFAULT_MAX_ELEMENTS = 5;

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

		int limit = maxElements;

		if (segments.size() < maxElements) {
			limit = segments.size();
		}

		for (int i = 0; i < limit; i++) {
			result.add(segments.get(i));
		}

		return result;
	}

}
