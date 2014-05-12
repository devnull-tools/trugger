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

package org.atatec.trugger.validation.impl;

import org.atatec.trugger.element.Element;
import org.atatec.trugger.element.ElementPredicates;
import org.atatec.trugger.element.Elements;
import org.atatec.trugger.interception.Interception;
import org.atatec.trugger.interception.ValidationInterceptionHandler;
import org.atatec.trugger.util.factory.ComponentFactory;
import org.atatec.trugger.validation.*;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.atatec.trugger.element.Elements.element;
import static org.atatec.trugger.reflection.ParameterPredicates.*;
import static org.atatec.trugger.reflection.Reflection.invoke;

/**
 * Default validator factory implementation.
 *
 * @author Marcelo Guimarães
 * @since 5.1
 */
public class TruggerValidatorFactory implements ValidatorFactory {

  private final Map<Class<? extends Annotation>, Validator> shared;

  public TruggerValidatorFactory() {
    shared = new ConcurrentHashMap<>();
  }

  private ComponentFactory<ValidatorClass, Validator> createFactory(Annotation annotation) {
    ComponentFactory<ValidatorClass, Validator> factory =
        new ComponentFactory<>(ValidatorClass.class);
    factory.toCreate(
        (constructor, args) -> {
          ArgumentsValidator argumentsValidator = new ArgumentsValidator(this);
          if (argumentsValidator.isValid(constructor, args)) {
            Validator validator = invoke(constructor).withArgs(args);
            Validator proxy = Interception.intercept(Validator.class)
                .on(validator)
                .onCall(new ValidationInterceptionHandler(this)
                    .onInvalid(context -> true))
                .proxy();
            if (validator.getClass().isAnnotationPresent(Shared.class)) {
              shared.put(annotation.annotationType(), proxy);
            }
            return proxy;
          }
          // there is no way to validate the value using this validator
          // so lets say that the value is not invalid (this is not the same
          // as saying that the value is valid according to the validation
          // API definitions)
          return (Validator) value -> true;
        }
    );
    return factory;
  }

  @Override
  public Validator create(Annotation annotation) {
    Class<? extends Annotation> type = annotation.annotationType();
    if (shared.containsKey(type)) {
      return shared.get(type);
    }
    return createFactory(annotation).create(annotation);
  }

  @Override
  public Validator create(Annotation annotation, Element element,
                          Object target, ValidationEngine engine) {
    Class<? extends Annotation> type = annotation.annotationType();
    if (shared.containsKey(type)) {
      return shared.get(type);
    }
    ComponentFactory<ValidatorClass, Validator> factory =
        createFactory(annotation);
    factory.toConfigure(ComponentFactory.defaults().andThen(
        (context, an) -> {
          context.use(element).when(type(Element.class));
          context.use(engine).when(type(ValidationEngine.class));
          context.use(target).when(annotatedWith(TargetObject.class));
          context.use(an).when(type(Annotation.class));
          context.use(TruggerValidatorFactory.this)
              .when(type(ValidatorFactory.class));

          List<Element> elements = Elements.elements().in(annotation);
          elements.stream()
              .filter(ElementPredicates.annotatedWith(TargetElement.class))
              .forEach(annotationElement -> {
                TargetElement targetElement = annotationElement
                    .getAnnotation(TargetElement.class);
                context.use(() -> element(annotationElement.get())
                    .in(target).get())
                    .when(named(targetElement.value()));
              });

          // force injection when "-parameters" are not used in compilation
          elements.stream().forEach(
              el -> context.use(() -> el.get()).when(type(el.type()))
          );
          elements.stream()
              .filter(ElementPredicates.annotatedWith(TargetElement.class))
              .forEach(annotationElement -> {
                Element tgElement = element(annotationElement.get()).in(target);
                context.use(() -> tgElement.get())
                    .when(e -> tgElement.type().isAssignableFrom(e.getType()));
              });
        }
    ));
    return factory.create(annotation);
  }

}
