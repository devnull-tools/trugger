/*
 * The Apache License
 *
 * Copyright 2009 Marcelo "Ataxexe" Guimarães <ataxexe@devnull.tools>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tools.devnull.trugger;

import java.util.List;

/**
 * Interface that defines a class capable of search a source for specific
 * objects.
 *
 * @param <T> the result type
 * @author Marcelo Guimarães
 * @since 2.0
 */
//TODO change to ElementFinder and receive the target type as generic object
public interface Finder<T> {

  /**
   * Finds the object with the specified name in a target.
   * <p>
   * The result may return <code>null</code> if no object with the specified
   * name is found.
   *
   * @param name   the object name to find.
   * @param target the target to search
   * @return the found object
   */
  T find(String name, Object target);

  /**
   * Finds all objects in a target.
   *
   * @param target the target to search
   * @return the found objects
   */
  List<T> findAll(Object target);

}
