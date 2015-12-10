package business.mocks;

import business.IOTestUtils;
import business.http.HttpService;

import java.io.IOException;

/**
 * @author mdelapenya
 */
public class MockHttpService implements HttpService {

	public MockHttpService(String idp) {
		this.idp = idp;
	}

	@Override
	public String get(String... arg) {
		try {
			return IOTestUtils.readFile("sample-" + idp + ".html");
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String idp;

}
