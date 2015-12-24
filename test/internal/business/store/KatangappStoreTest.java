package internal.business.store;

import business.exception.APIException;
import business.store.Store;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author mdelapenya
 */
public class KatangappStoreTest {

	@Rule
	public ExpectedException thrownException = ExpectedException.none();

	@Before
	public void setUp() {
		store = KatangappStore.getInstance();
	}

	@Test
	public void testGetBusStopNotFound() throws APIException {
		thrownException.expect(APIException.class);

		store.getBusStop("notfound");
	}

	@Test
	public void testGetRouteNotFound() throws APIException {
		thrownException.expect(APIException.class);

		store.getRoute("notfound");
	}

	private Store store;

}