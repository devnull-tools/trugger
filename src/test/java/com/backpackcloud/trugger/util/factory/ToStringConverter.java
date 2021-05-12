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

package com.backpackcloud.trugger.util.factory;

public class ToStringConverter implements Converter<Object, String> {

  private final String ifNull;

  public ToStringConverter(String ifNull) {
    this.ifNull = ifNull;
  }

  public ToStringConverter(ToString annotation) {
    this(annotation.ifNull());
  }

  @Override
  public String convert(Object object) {
    return object == null ? ifNull : object.toString();
  }

}