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
package com.backpackcloud.trugger.interception.impl;

import com.backpackcloud.trugger.interception.InterceptionContext;
import com.backpackcloud.trugger.TruggerException;
import com.backpackcloud.trugger.reflection.ReflectedMethod;
import com.backpackcloud.trugger.reflection.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This class holds the parameters of a method interception.
 *
 * @author Marcelo Guimaraes
 * @since 5.0
 */
public class InterceptionContextImpl implements InterceptionContext {

  private final Object target;

  /**
   * The proxy instance that the method was invoked on
   */
  private final Object proxy;

  /**
   * The <code>Method</code> instance corresponding to the method invoked on the
   * proxy instance
   */
  private final Method method;

  /**
   * The values of the arguments passed in the method invocation on the proxy
   * instance, or an empty array if interface method takes no arguments.
   */
  private final Object[] args;

  /**
   * Creates a new InterceptionContextImpl
   *
   * @param proxy  the proxy instance that the method was invoked on
   * @param method the <code>Method</code> instance corresponding to the method
   *               invoked on the proxy instance
   * @param args   the arguments passed in the method invocation on the proxy
   *               instance
   */
  public InterceptionContextImpl(Object target, Object proxy, Method method,
                                 Object[] args) {
    this.target = target;
    this.proxy = proxy;
    this.method = method;
    this.args = args;
  }

  @Override
  public Object invoke() throws Throwable {
    return invokeOn(target);
  }

  @Override
  public Object invokeOn(Object target) throws Throwable {
    try {
      Method targetMethod = methodOn(target);
      Reflection.setAccessible(targetMethod);
      return targetMethod.invoke(target, args);
    } catch (InvocationTargetException e) {
      throw e.getCause();
    }
  }

  @Override
  public Method methodOn(Object target) {
    String name = method.getName();
    Class<?>[] parameterTypes = method.getParameterTypes();
    Method targetMethod = Reflection.reflect()
        .method(name)
        .deep()
        .withParameters(parameterTypes)
        .from(target)
        .map(ReflectedMethod::unwrap)
        .orElseThrow(TruggerException::new);
    if (targetMethod.isBridge()) {
      return Reflection.reflect()
          .bridgedMethodFor(targetMethod)
          .orElseThrow(TruggerException::new);
    }
    return targetMethod;
  }

  @Override
  public Object target() {
    return target;
  }

  @Override
  public Object[] args() {
    return args;
  }

  @Override
  public Object proxy() {
    return proxy;
  }

  @Override
  public Method method() {
    return method;
  }

}
