/*
 * The Apache License
 *
 * Copyright 2009 Marcelo Guimaraes <ataxexe@backpackcloud.com>
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

package com.backpackcloud.trugger.reflection;

/**
 * Interface for selecting the generic type.
 *
 * @author Marcelo Guimaraes
 * @since 6.0
 */
public interface GenericTypeSelector {

  /**
   * Sets the target for selecting the generic type
   *
   * @param target the target to use
   * @return the generic type on the given target.
   */
  Class of(Object target);

}
