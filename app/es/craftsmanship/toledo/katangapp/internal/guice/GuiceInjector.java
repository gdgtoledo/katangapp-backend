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

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author mdelapenya
 */
public class GuiceInjector {

	public <T> T getInjectedInstance(Class<T> clazz) {
		return injector.getInstance(clazz);
	}

	public static GuiceInjector getInstance() {
		return instance;
	}

	private GuiceInjector() {
		injector = Guice.createInjector(
			new ClosestPointAlgorithmModule(), new FinderModule(),
			new ParserModule());
	}

	private static GuiceInjector instance = new GuiceInjector();

	private Injector injector;

}