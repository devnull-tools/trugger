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

import java.util.Map;

/**
 * @author Marcelo Guimaraes
 */
public class MapElement extends AbstractElement implements Element {

  public MapElement(String name) {
    super(name);
  }

  @Override
  public ValueHandler on(Object target) {
    if (target instanceof Map<?, ?>) {
      final Map map = (Map) target;
      return new ValueHandler() {

        @Override
        public void setValue(Object value) throws HandlingException {
          try {
            map.put(name, value);
          } catch (UnsupportedOperationException e) {
            throw new HandlingException(e);
          }
        }

        @Override
        public <E> E getValue() throws HandlingException {
          if (map.containsKey(name)) {
            return (E) map.get(name);
          }
          throw new HandlingException("Key '" + name + "' not present in target.");
        }
      };
    }
    throw new IllegalArgumentException("Target is not a " + Map.class);
  }

  @Override
  public Class<?> declaringClass() {
    return Map.class;
  }

  @Override
  public boolean isReadable() {
    return true;
  }

  @Override
  public boolean isWritable() {
    return true;
  }

}
