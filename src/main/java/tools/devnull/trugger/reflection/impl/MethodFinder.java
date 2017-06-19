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
package tools.devnull.trugger.reflection.impl;

import java.lang.reflect.Method;

/**
 * @author Marcelo "Ataxexe" Guimarães
 */
public class MethodFinder implements MemberFinder<Method> {
  
  private final String name;
  private final Class[] parameterTypes;
  
  public MethodFinder(String name, Class[] parameterTypes) {
    this.name = name;
    this.parameterTypes = parameterTypes;
  }

  @Override
  public Method find(Class<?> type) throws Exception {
    return type.getMethod(name, parameterTypes);
  }
  
}
