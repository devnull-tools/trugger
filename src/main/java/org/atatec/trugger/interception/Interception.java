/*
 * Copyright 2009-2014 Marcelo Guimarães
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.atatec.trugger.interception;

/**
 * Interface that defines an action while intercepting a method call.
 *
 * @author Marcelo Guimarães
 * @since 4.1
 */
@FunctionalInterface
public interface Interception {

  /**
   * Intercepts the method call.
   *
   * @param context the context of the interception
   * @return a return value or <code>null</code> if the method is void.
   * @throws Throwable if any error occurs
   */
  Object intercept(InterceptionContext context) throws Throwable;

  /**
   * Combines this interception with another one.
   *
   * @param other the other interception to execute after this
   * @return the new Interception
   */
  default Interception andThen(Interception other) {
    return context -> {
      this.intercept(context);
      return other.intercept(context);
    };
  }

}
