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
package org.atatec.trugger.test.validator;

import junit.framework.Assert;
import org.atatec.trugger.element.Element;
import org.atatec.trugger.reflection.Reflection;
import org.atatec.trugger.util.mock.AnnotationMockBuilder;
import org.atatec.trugger.validation.Validation;
import org.atatec.trugger.validation.ValidatorContext;
import org.atatec.trugger.validation.ValidatorFactory;
import org.atatec.trugger.validation.ValidatorInvoker;
import org.atatec.trugger.validation.impl.ValidatorContextImpl;
import org.junit.Before;

import java.lang.annotation.Annotation;

import static org.atatec.trugger.util.mock.Mock.annotation;
import static org.atatec.trugger.util.mock.Mock.mock;

/**
 * A base class for testing the validators.
 *
 * @author Marcelo Varella Barca Guimarães
 */
public abstract class ValidatorTest<T extends Annotation> {

  protected final ValidatorFactory factory = Validation.newValidatorFactory();
  protected ValidatorInvoker validator;
  protected T annotation;
  protected AnnotationMockBuilder<T> builder;
  private final Class<T> annotationType;

  public ValidatorTest() {
    annotationType = Reflection.reflect().genericType("T").in(this);
  }

  @Before
  public void initialize() {
    builder = new AnnotationMockBuilder<T>(annotationType);
    annotation = builder.annotation();
    validator = null;
  }

  protected final void createValidator(ValidatorContext context) {
    validator = factory.create(context);
  }

  protected final void createValidator(Annotation annotation, Object target) {
    validator = factory.create(new ValidatorContextImpl(annotation, target));
  }

  protected final void createValidator(Element element, Object target) {
    validator = factory.create(new ValidatorContextImpl(builder.mock(), element, target));
  }

  protected final void createValidator(Object target) {
    validator = factory.create(new ValidatorContextImpl(builder.mock(), null, target));
  }

  protected final void createValidator(Class<? extends Annotation> annotationType) {
    validator = factory.create(new ValidatorContextImpl(mock(annotation(annotationType))));
  }

  protected final void createValidator() {
    validator = factory.create(new ValidatorContextImpl(builder.mock()));
  }

  protected final void assertTypeDisallowed(Object value) {
    if(validator == null) {
      createValidator();
    }
    boolean error = false;
    try {
      validator.isValid(value);
    } catch (IllegalArgumentException e) {
      error = true;
    }
    Assert.assertTrue(error);
  }

  protected final void assertValid(Object value) {
    if(validator == null) {
      createValidator();
    }
    Assert.assertTrue(validator.isValid(value));
  }

  protected final void assertInvalid(Object value) {
    if(validator == null) {
      createValidator();
    }
    Assert.assertFalse(validator.isValid(value));
  }

}