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

package com.backpackcloud.trugger.predicate;

import org.junit.Test;

import static com.backpackcloud.trugger.TruggerTest.assertMatch;
import static com.backpackcloud.trugger.TruggerTest.assertNotMatch;
import static com.backpackcloud.trugger.reflection.ClassPredicates.subtypeOf;
import static com.backpackcloud.trugger.reflection.ClassPredicates.ofType;

public class ClassPredicatesTest {

  @Test
  public void testSubtypePredicate() {
    assertMatch(Exception.class, subtypeOf(Throwable.class));
    assertNotMatch(Throwable.class, subtypeOf(Exception.class));
    assertNotMatch(Throwable.class, subtypeOf(Throwable.class));
  }

  @Test
  public void testTypePredicate() {
    assertNotMatch(Exception.class, ofType(Throwable.class));
    assertNotMatch(Throwable.class, ofType(Exception.class));
    assertMatch(Throwable.class, ofType(Throwable.class));
  }

}
