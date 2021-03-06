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
package com.backpackcloud.trugger.interception;

import com.backpackcloud.trugger.TruggerException;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static com.backpackcloud.trugger.interception.Interception.intercept;
import static com.backpackcloud.trugger.interception.InterceptionHandler.delegate;

/**
 * @author Marcelo Guimaraes
 */
public class InterceptorTest {

  static interface MyInterface {

    Object doIt(Object argument) throws IOException;

  }

  static class InvocationTest implements MyInterface {

    @Override
    public Object doIt(Object argument) {
      return argument;
    }

  }

  static class ExceptionHandlingTest implements MyInterface {

    @Override
    public Object doIt(Object argument) {
      throw new IllegalArgumentException();
    }

  }

  @Test
  public void testInterception() throws IOException {
    MyInterface obj = intercept(new InvocationTest())
        .with(ClassLoader.getSystemClassLoader())
        .onCall(context -> context.invoke())
        .proxy();
    Object argument = new Object();
    assertSame(argument, obj.doIt(argument));
  }

  @Test
  public void testInterfaceExceptionHandling() throws IOException {
    MyInterface obj = intercept(new ExceptionHandlingTest())
        .onCall(context -> context.invoke())
        .onFail((context, error) -> "pass")
        .proxy();

    assertEquals("pass", obj.doIt(null));
  }

  @Test(expected = TruggerException.class)
  public void testInterfaceInterception() throws IOException {
    MyInterface obj = intercept(MyInterface.class)
        .onCall(context -> {
          throw new IllegalArgumentException();
        })
        .onFail((context, error) -> {
          throw new TruggerException(error);
        })
        .proxy();
    obj.doIt(null);
  }

  @Test(expected = IOException.class)
  public void testException() throws IOException {
    MyInterface obj = intercept(MyInterface.class)
        .onCall(context -> {
          throw new IOException();
        })
        .proxy();
    obj.doIt(null);
  }

  @Test
  public void testInterfacesInterception() {
    Object obj = intercept(Function.class, Predicate.class)
        .onCall(context -> true)
        .proxy();
    assertTrue(((Function<Object, Boolean>) obj).apply(null));
    assertTrue(((Predicate) obj).test(null));
  }

  @Test
  public void testComposition() throws Exception {
    MyInterface obj = mock(MyInterface.class);
    when(obj.doIt("test")).thenReturn(null);

    MyInterface proxy = intercept(MyInterface.class)
        .on(obj)
        .onCall(delegate().andThen(
            context -> "notNull"
        ))
        .proxy();

    assertNotNull(proxy.doIt("test"));

    verify(obj).doIt("test");
  }

}
