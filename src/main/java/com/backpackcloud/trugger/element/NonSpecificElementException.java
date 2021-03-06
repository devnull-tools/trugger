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

import com.backpackcloud.trugger.HandlingException;

/**
 * Exception thrown when a direct value handling is trying on a non-specific
 * element.
 *
 * @author Marcelo Guimaraes
 * @since 2.0
 */
public class NonSpecificElementException extends HandlingException {

  private static final long serialVersionUID = -7252352629846011634L;

  /**
   * Creates a new NonSpecificElementException.
   */
  public NonSpecificElementException() {
    super();
  }

  /**
   * Creates a new NonSpecificElementException with the given message.
   *
   * @param message
   *          the exception message
   */
  public NonSpecificElementException(String message) {
    super(message);
  }

  /**
   * Creates a new NonSpecificElementException with the given cause and message.
   *
   * @param message
   *          the exception message
   * @param cause
   *          the exception cause
   */
  public NonSpecificElementException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new NonSpecificElementException with the given cause.
   *
   * @param cause
   *          the exception cause
   */
  public NonSpecificElementException(Throwable cause) {
    super(cause);
  }

}
