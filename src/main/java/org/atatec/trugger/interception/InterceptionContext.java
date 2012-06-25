/*
 * Copyright 2009-2012 Marcelo Varella Barca Guimarães
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

import java.lang.reflect.Method;

/**
 * This class holds the parameters of a method interception.
 *
 * @author Marcelo Varella Barca Guimarães
 * @since 2.1
 */
public class InterceptionContext {

  /**
   * The proxy instance that the method was invoked on
   */
  public final Object proxy;

  /**
   * The <code>Method</code> instance corresponding to the method invoked on the
   * proxy instance
   */
  public final Method method;

  /**
   * The values of the arguments passed in the method invocation on the proxy
   * instance, or <code>null</code> if interface method takes no arguments
   */
  public final Object[] args;

  /**
   * Creates a new InterceptionContext
   *
   * @param proxy
   *          the proxy instance that the method was invoked on
   * @param method
   *          the <code>Method</code> instance corresponding to the method
   *          invoked on the proxy instance
   * @param args
 *          an array of objects containing the values of the arguments passed
 *          in the method invocation on the proxy instance, or
   */
  InterceptionContext(Object proxy, Method method, Object[] args) {
    this.proxy = proxy;
    this.method = method;
    this.args = args;
  }

}