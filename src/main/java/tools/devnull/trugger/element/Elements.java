/*
 * Copyright 2009-2012 Marcelo Guimarães
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
package tools.devnull.trugger.element;

import tools.devnull.trugger.Finder;
import tools.devnull.trugger.HandlingException;
import tools.devnull.trugger.ValueHandler;
import tools.devnull.trugger.loader.ImplementationLoader;
import tools.devnull.trugger.registry.Registry;
import tools.devnull.trugger.selector.ElementSelector;
import tools.devnull.trugger.selector.ElementsSelector;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class for helping {@link Element} selection.
 *
 * @author Marcelo Guimarães.
 * @since 1.2
 */
public class Elements {

  private static final ElementFactory factory;

  private Elements() {
  }

  static {
    factory = ImplementationLoader.get(ElementFactory.class);
  }

  /**
   * @return the registry.
   *
   * @since 2.3
   */
  public static Registry<Class<?>, Finder<Element>> registry() {
    return factory.registry();
  }

  /**
   * Selects an element.
   *
   * @param name the element name.
   *
   * @return a component for selecting the element.
   */
  public static ElementSelector element(String name) {
    return factory.createElementSelector(name);
  }

  /**
   * Selects an element without specifying a name.
   *
   * @return a component for selecting the element.
   *
   * @since 2.5
   */
  public static ElementSelector element() {
    return factory.createElementSelector();
  }

  /**
   * Selects a set of elements.
   *
   * @return a component for selecting the elements.
   */
  public static ElementsSelector elements() {
    return factory.createElementsSelector();
  }

  /**
   * Copies elements through objects.
   */
  public static ElementCopier copy() {
    return factory.createElementCopier();
  }

  /** Copies the elements returned by the given selector through objects. */
  public static ElementCopier copy(ElementsSelector selector) {
    return factory.createElementCopier(selector);
  }

  /**
   * Handles a collection of {@link Element#isSpecific() specific} elements.
   * <p>
   * This ValueHandler can set a common value to all elements and retrieve a Collection of
   * all element values.
   *
   * @param elements the elements to handle
   *
   * @return a ValueHandler for handle multiple Element objects.
   *
   * @since 2.4
   */
  public static ValueHandler handle(final Collection<Element> elements) {
    return new ValueHandler() {

      @Override
      public void value(Object value) throws HandlingException {
        for (Element element : elements) {
          element.value(value);
        }
      }

      @Override
      public <E> E value() throws HandlingException {
        Collection result = new ArrayList();
        for (Element element : elements) {
          result.add(element.value());
        }
        return (E) result;
      }
    };
  }

  /**
   * Handles a collection of {@link Element#isSpecific() non specific} elements.
   * <p>
   * This ValueHandler can set a common value to all elements and retrieve a Collection of
   * all element values.
   *
   * @param elements the elements to handle
   * @param target   the target
   *
   * @return a ValueHandler for handle multiple Element objects.
   *
   * @since 2.4
   */
  public static ValueHandler handle(final Collection<Element> elements, final Object target) {
    return new ValueHandler() {

      @Override
      public void value(Object value) throws HandlingException {
        for (Element element : elements) {
          element.in(target).value(value);
        }
      }

      @Override
      public <E> E value() throws HandlingException {
        Collection result = new ArrayList();
        for (Element element : elements) {
          result.add(element.in(target).value());
        }
        return (E) result;
      }
    };
  }

}
