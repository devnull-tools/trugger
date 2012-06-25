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
package org.atatec.trugger.element.impl;

import org.atatec.trugger.Finder;
import org.atatec.trugger.Result;
import org.atatec.trugger.element.Element;
import org.atatec.trugger.util.Utils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.atatec.trugger.reflection.Reflection.methods;

/**
 * A default class for finding properties in annotations.
 * <p>
 * All methods declared on the annotation will be treat as a property.
 *
 * @author Marcelo Varella Barca Guimarães
 */
public final class AnnotationElementFinder implements Finder<Element> {

  private final Map<Class<?>, Map<String, Element>> cache;
  private static final int MAX_SIZE = 200;

  /**
   * Creates a new finder
   */
  public AnnotationElementFinder() {
    cache = new ConcurrentHashMap<Class<?>, Map<String, Element>>(MAX_SIZE);
  }

  public Result<Set<Element>, Object> findAll() {
    return new Result<Set<Element>, Object>() {

      public Set<Element> in(Object target) {
        Class<?> annotationType = Utils.resolveType(target);
        Collection<Element> elements = getFromCache(annotationType).values();
        return ElementFinderHelper.computeResult(target, elements);
      }
    };
  }

  public final Result<Element, Object> find(final String propertyName) {
    return new Result<Element, Object>() {

      public Element in(Object target) {
        Element property = getFromCache(Utils.resolveType(target)).get(propertyName);
        if (target instanceof Class<?>) {
          return property;
        } else if(property != null) {
          return new SpecificElement(property, target);
        }
        return null;
      }
    };
  }

  private Map<String, Element> getFromCache(Class<?> annotationType) {
    Map<String, Element> map = cache.get(annotationType);
    if (map == null) {
      map = new HashMap<String, Element>(20);
      cache.put(annotationType, map);
      Set<Method> declaredMethods = methods().in(annotationType);
      AnnotationElement prop;
      for (Method method : declaredMethods) {
        prop = new AnnotationElement(method);
        map.put(prop.name(), prop);
      }
    }
    return map;
  }

}