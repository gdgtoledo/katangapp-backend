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

package es.craftsmanship.toledo.katangapp.internal.guice;

import es.craftsmanship.toledo.katangapp.api.Finder;
import es.craftsmanship.toledo.katangapp.api.http.HttpService;
import es.craftsmanship.toledo.katangapp.internal.BusStopsFinder;
import es.craftsmanship.toledo.katangapp.internal.http.UnautoHttpService;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author mdelapenya
 */
public class FinderModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Finder.class).to(BusStopsFinder.class);
	}

	@Provides
	HttpService provideHttpService() {
		HttpService httpService = new UnautoHttpService();

		return httpService;
	}

}