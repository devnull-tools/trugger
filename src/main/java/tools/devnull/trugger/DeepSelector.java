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

/**
 * Interface that defines a selector that allows deep operations.
 *
 * @author Marcelo "Ataxexe" Guimarães
 * @since 5.0
 */
public interface DeepSelector {

  /**
   * Selects using a deep operation. If the selection is for a single object
   * and the deep operation founds more than one, the first one will be
   * returned.
   *
   * @return a new selector with deep selection enabled
   */
  DeepSelector deep();

}
