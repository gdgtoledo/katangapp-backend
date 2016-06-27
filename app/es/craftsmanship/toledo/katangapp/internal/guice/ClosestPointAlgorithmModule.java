package es.craftsmanship.toledo.katangapp.internal.guice;

import es.craftsmanship.toledo.katangapp.business.ClosestPointsAlgorithm;
import es.craftsmanship.toledo.katangapp.internal.algorithm.SegmentsAlgorithm;

import com.google.inject.AbstractModule;

/**
 * @author mdelapenya
 */
public class ClosestPointAlgorithmModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ClosestPointsAlgorithm.class).to(SegmentsAlgorithm.class);
	}

}