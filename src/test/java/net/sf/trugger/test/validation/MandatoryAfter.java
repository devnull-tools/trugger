/*
 * Copyright 2009-2010 Marcelo Varella Barca Guimarães
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
package net.sf.trugger.test.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import net.sf.trugger.date.DateType;
import net.sf.trugger.validation.ValidatorClass;
import net.sf.trugger.validation.validator.After;
import net.sf.trugger.validation.validator.GroupValidator;
import net.sf.trugger.validation.validator.NotNull;

@NotNull
@After(reference = "parting", type = DateType.DATE_TIME, validIfEquals = false)
@ValidatorClass(GroupValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MandatoryAfter {}
