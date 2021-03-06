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
package com.backpackcloud.trugger.element;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Marcelo Guimaraes
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
  
  boolean bool();
  
  String name() default "None";
  
  int number();
  
}
