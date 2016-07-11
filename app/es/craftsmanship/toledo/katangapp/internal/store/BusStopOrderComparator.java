package es.craftsmanship.toledo.katangapp.internal.store;

import es.craftsmanship.toledo.katangapp.models.BusStop;

import java.util.Comparator;

/**
 * @author Manuel de la Peña
 */
public class BusStopOrderComparator implements Comparator<BusStop> {

	@Override
	public int compare(BusStop busStop1, BusStop busStop2) {
		float order1 = Float.valueOf(busStop1.getOrder());
		float order2 = Float.valueOf(busStop2.getOrder());

		if (order1 > order2) {
			return 1;
		}
		else if (order1 < order2) {
			return -1;
		}
		else {
			return 0;
		}
	}

}