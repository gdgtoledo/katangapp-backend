package es.craftsmanship.toledo.katangapp.test;

/**
 * @author mdelapenya
 */
public class SpecsContants {

	public static final String BUS_STOP_JSON =
		"{\"address\":\"Corralillo de San Miguel\"," +
			"\"id\":\"P001\",\"latitude\":39.858966,\"links\":{\"self\":" +
			"\"/api/busStops/P001\"},\"longitude\":-4.020902,\"routes\":" +
			"[{\"orderId\":\"1.00000\",\"routeId\":\"41\"}," +
			"{\"orderId\":\"96.00000\",\"routeId\":\"41\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"41b\"}," +
			"{\"orderId\":\"111.00000\",\"routeId\":\"41b\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"42\"}," +
			"{\"orderId\":\"107.00000\",\"routeId\":\"42\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L01\"}," +
			"{\"orderId\":\"158.00000\",\"routeId\":\"L01\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L03\"}," +
			"{\"orderId\":\"190.00000\",\"routeId\":\"L03\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L05\"}," +
			"{\"orderId\":\"181.00000\",\"routeId\":\"L05\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L10\"}," +
			"{\"orderId\":\"173.00000\",\"routeId\":\"L10\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L11\"}," +
			"{\"orderId\":\"71.00000\",\"routeId\":\"L11\"}," +
			"{\"orderId\":\"171.00000\",\"routeId\":\"L12\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L5d\"}," +
			"{\"orderId\":\"113.00000\",\"routeId\":\"L5d\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L61\"}," +
			"{\"orderId\":\"205.00000\",\"routeId\":\"L61\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L62\"}," +
			"{\"orderId\":\"253.00000\",\"routeId\":\"L62\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L71\"}," +
			"{\"orderId\":\"186.00000\",\"routeId\":\"L71\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L72\"}," +
			"{\"orderId\":\"105.00000\",\"routeId\":\"L72\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L72b\"}," +
			"{\"orderId\":\"135.00000\",\"routeId\":\"L72b\"}," +
			"{\"orderId\":\"1.00000\",\"routeId\":\"L94\"}," +
			"{\"orderId\":\"208.00000\",\"routeId\":\"L94\"}]}";

	public static final String BUS_STOP_PRETTIFIED_JSON = "{\n" +
		"  \"address\" : \"Corralillo de San Miguel\",\n" +
		"  \"id\" : \"P001\",\n" +
		"  \"latitude\" : 39.858966,\n" +
		"  \"links\" : {\n" +
		"    \"self\" : \"/api/busStops/P001\"\n" +
		"  },\n" +
		"  \"longitude\" : -4.020902,\n" +
		"  \"routes\" : [ {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"41\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"96.00000\",\n" +
		"    \"routeId\" : \"41\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"41b\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"111.00000\",\n" +
		"    \"routeId\" : \"41b\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"42\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"107.00000\",\n" +
		"    \"routeId\" : \"42\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L01\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"158.00000\",\n" +
		"    \"routeId\" : \"L01\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L03\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"190.00000\",\n" +
		"    \"routeId\" : \"L03\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L05\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"181.00000\",\n" +
		"    \"routeId\" : \"L05\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L10\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"173.00000\",\n" +
		"    \"routeId\" : \"L10\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L11\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"71.00000\",\n" +
		"    \"routeId\" : \"L11\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"171.00000\",\n" +
		"    \"routeId\" : \"L12\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L5d\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"113.00000\",\n" +
		"    \"routeId\" : \"L5d\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L61\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"205.00000\",\n" +
		"    \"routeId\" : \"L61\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L62\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"253.00000\",\n" +
		"    \"routeId\" : \"L62\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L71\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"186.00000\",\n" +
		"    \"routeId\" : \"L71\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L72\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"105.00000\",\n" +
		"    \"routeId\" : \"L72\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L72b\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"135.00000\",\n" +
		"    \"routeId\" : \"L72b\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"1.00000\",\n" +
		"    \"routeId\" : \"L94\"\n" +
		"  }, {\n" +
		"    \"orderId\" : \"208.00000\",\n" +
		"    \"routeId\" : \"L94\"\n" +
		"  } ]\n" +
		"}";

	public static final int SERVER_PORT = 3333;

	public static final int TIMEOUT = 10000;

}
