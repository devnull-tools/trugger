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
package com.backpackcloud.trugger.element.impl;

import com.backpackcloud.trugger.HandlingException;
import com.backpackcloud.trugger.ValueHandler;
import com.backpackcloud.trugger.element.Element;

import java.lang.annotation.Annotation;

/**
 * A class that represents a {@link Element#isSpecific() specific} element.
 *
 * @author Marcelo Guimaraes
 */
public class SpecificElement implements Element {

  private final Object target;
  private final Element element;

  /**
   * @param element the specific element.
   * @param target  the target for this specific element.
   */
  public SpecificElement(Element element, Object target) {
    this.element = element;
    this.target = target;
  }

  @Override
  public boolean isSpecific() {
    return true;
  }

  public <E> E target() {
    return (E) target;
  }

  @Override
  public Object getValue() throws HandlingException {
    return on(target).getValue();
  }

  @Override
  public void setValue(Object value) throws HandlingException {
    on(target).setValue(value);
  }

  public Class declaringClass() {
    return element.declaringClass();
  }

  public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
    return element.getAnnotation(annotationClass);
  }

  public Annotation[] getAnnotations() {
    return element.getAnnotations();
  }

  public Annotation[] getDeclaredAnnotations() {
    return element.getDeclaredAnnotations();
  }

  public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
    return element.isAnnotationPresent(annotationClass);
  }

  public String name() {
    return element.name();
  }

  public Class type() {
    return element.type();
  }

  @Override
  public ValueHandler on(Object target) {
    return element.on(target);
  }

  public boolean isReadable() {
    return element.isReadable();
  }

  public boolean isWritable() {
    return element.isWritable();
  }

  @Override
  public String toString() {
    return element.toString();
  }

}
