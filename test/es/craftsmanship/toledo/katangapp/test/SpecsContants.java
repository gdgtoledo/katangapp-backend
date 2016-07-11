package es.craftsmanship.toledo.katangapp.test;

/**
 * @author mdelapenya
 */
public class SpecsContants {

	public static final String BUS_STOP_JSON =
		"{\"address\":\"Corralillo de San Miguel\"," +
			"\"id\":\"P001\",\"latitude\":39.858966," +
			"\"longitude\":-4.020902,\"order\":null," +
			"\"routeId\":null}";

	public static final String BUS_STOP_PRETTIFIED_JSON = "{\n" +
		"  \"address\" : \"Corralillo de San Miguel\",\n" +
		"  \"id\" : \"P001\",\n" +
		"  \"latitude\" : 39.858966,\n" +
		"  \"longitude\" : -4.020902,\n" +
		"  \"order\" : null,\n" +
		"  \"routeId\" : null\n" +
		"}";

	public static final int SERVER_PORT = 3333;

	public static final int TIMEOUT = 10000;

}
