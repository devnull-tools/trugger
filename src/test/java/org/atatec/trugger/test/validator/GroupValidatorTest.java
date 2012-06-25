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

import org.junit.Test;

/**
 * @author Marcelo Varella Barca Guimarães
 */
public class GroupValidatorTest extends ValidatorTest<Validations> {

  @Test
  public void testValidator() {
    createValidator();

    assertInvalid(null);
    assertInvalid("a");
    assertInvalid("abd3");
    assertInvalid("abdgery");
    assertTypeDisallowed(Object.class);
    assertValid("abafb");
  }

}