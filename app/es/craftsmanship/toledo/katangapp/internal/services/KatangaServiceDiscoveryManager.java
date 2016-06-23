package es.craftsmanship.toledo.katangapp.internal.services;

import es.craftsmanship.toledo.katangapp.business.http.HttpService;
import es.craftsmanship.toledo.katangapp.services.StatusCheckService;
import es.craftsmanship.toledo.katangapp.services.StatusCheckServiceDiscoveryManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mdelapenya
 */
public class KatangaServiceDiscoveryManager
	implements StatusCheckServiceDiscoveryManager {

	public KatangaServiceDiscoveryManager(HttpService httpService) {
		this.httpService = httpService;

		this.statusCheckServices = new ArrayList<>();

		statusCheckServices.add(
			new UnautoStatusService(
				this.httpService, new String[]{"L94", "P001", "208.00000"}));
	}

	@Override
	public List<StatusCheckService> getServices() {
		return statusCheckServices;
	}

	private List<StatusCheckService> statusCheckServices;

	private final HttpService httpService;

}