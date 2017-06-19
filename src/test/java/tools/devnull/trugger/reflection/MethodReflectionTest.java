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
package tools.devnull.trugger.reflection;

import org.junit.Test;
import tools.devnull.trugger.Flag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static tools.devnull.trugger.reflection.MethodPredicates.annotated;
import static tools.devnull.trugger.reflection.MethodPredicates.annotatedWith;
import static tools.devnull.trugger.reflection.Reflection.invoke;
import static tools.devnull.trugger.reflection.Reflection.reflect;

/**
 * A class for testing method reflection by the {@link Reflector}.
 *
 * @author Marcelo Varella Barca Guimarães
 */
public class MethodReflectionTest {

  interface TestInterface {

    void doIt();

    void foo();

    @Flag
    void bar();

  }

  @Test
  public void invokerTest() {
    TestInterface obj = mock(TestInterface.class);
    reflect().method("doIt").from(obj).and(invoke());
    verify(obj).doIt();
  }

  @Test
  public void invokerExceptionHandlerTest() {
    TestInterface obj = mock(TestInterface.class);
    doThrow(new IllegalArgumentException()).when(obj).doIt();
    try {
      reflect().method("doIt").from(obj).and(invoke());
      throw new AssertionError();
    } catch (ReflectionException e) {
      assertTrue(IllegalArgumentException.class.equals(e.getCause().getClass()));
    }

    verify(obj).doIt();
  }

  @Test
  public void invokerForNoMethodTest() {
    TestInterface obj = mock(TestInterface.class);
    reflect().method("notDeclared").from(obj).and(invoke());
  }

  @Test
  public void predicatesTest() {
    assertFalse(
        annotated().test(
            reflect().method("doIt").from(TestInterface.class).result()
        )
    );
    assertFalse(
        annotatedWith(Flag.class).test(
            reflect().method("doIt").from(TestInterface.class).result()
        )
    );
    assertTrue(
        annotated().test(
            reflect().method("bar").from(TestInterface.class).result()
        )
    );
    assertTrue(
        annotatedWith(Flag.class).test(
            reflect().method("bar").from(TestInterface.class).result()
        )
    );
  }

}
