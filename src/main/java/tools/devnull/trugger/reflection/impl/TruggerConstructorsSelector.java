/*
 * The Apache License
 *
 * Copyright 2009 Marcelo "Ataxexe" Guimarães <ataxexe@devnull.tools>
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
package tools.devnull.trugger.reflection.impl;

import tools.devnull.trugger.reflection.ConstructorsSelector;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.function.Predicate;

/**
 * A default implementation for the constructors selector.
 *
 * @author Marcelo "Ataxexe" Guimarães
 */
public final class TruggerConstructorsSelector implements ConstructorsSelector {

  private final MembersFinder<Constructor<?>> finder;
  private final Predicate<? super Constructor<?>> predicate;

  public TruggerConstructorsSelector(MembersFinder<Constructor<?>> finder) {
    this.finder = finder;
    this.predicate = null;
  }

  public TruggerConstructorsSelector(
      MembersFinder<Constructor<?>> finder,
      Predicate<? super Constructor<?>> predicate) {
    this.finder = finder;
    this.predicate = predicate;
  }

  public ConstructorsSelector filter(
      Predicate<? super Constructor<?>> predicate) {
    return new TruggerConstructorsSelector(finder, predicate);
  }

  public List<Constructor<?>> from(Object target) {
    return new MembersSelector<>(finder, predicate).selectFrom(target);
  }

}
