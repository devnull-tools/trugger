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
package net.sf.trugger.test.reflection;

import static net.sf.trugger.reflection.Reflection.reflect;
import static net.sf.trugger.reflection.ReflectionPredicates.ANNOTATED;
import static net.sf.trugger.reflection.ReflectionPredicates.NON_FINAL;
import static net.sf.trugger.reflection.ReflectionPredicates.NON_STATIC;
import static net.sf.trugger.reflection.ReflectionPredicates.NOT_ANNOTATED;
import static net.sf.trugger.reflection.ReflectionPredicates.annotatedWith;
import static net.sf.trugger.reflection.ReflectionPredicates.notAnnotatedWith;
import static net.sf.trugger.test.TruggerTest.assertMatch;
import static net.sf.trugger.test.TruggerTest.assertNoResult;
import static net.sf.trugger.test.TruggerTest.assertResult;
import static net.sf.trugger.test.TruggerTest.assertThrow;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Method;

import net.sf.trugger.predicate.Predicates;
import net.sf.trugger.reflection.Access;
import net.sf.trugger.reflection.ReflectionException;
import net.sf.trugger.selector.MethodSelector;
import net.sf.trugger.test.Flag;
import net.sf.trugger.test.SelectionTestAdapter;

import org.junit.Test;

/**
 * @author Marcelo Varella Barca Guimarães
 */
public class NoNamedMethodSelectorTest {

  static class SimpleObject {
    void foo(){};
  }

  @Test
  public void simpleTest() {
    assertResult(reflect().method().in(SimpleObject.class));
  }

  static class AnnotatedSelectorTest {
    @Flag
    void foo(){};
    void bar(){};
  }

  @Test(expected = ReflectionException.class)
  public void testMultipleMatch() {
    reflect().method().in(AnnotatedSelectorTest.class);
  }

  @Test
  public void testAnnotatedSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.annotated();
      }
      @Override
      public void assertions(Method method) {
        assertMatch(method, ANNOTATED);
      }
    }, AnnotatedSelectorTest.class);
  }

  @Test
  public void testNotAnnotatedSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.notAnnotated();
      }
      @Override
      public void assertions(Method method) {
        assertMatch(method, NOT_ANNOTATED);
      }
    }, AnnotatedSelectorTest.class);
  }

  @Test
  public void testAnnotatedWithSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.annotatedWith(Flag.class);
      }
      @Override
      public void assertions(Method method) {
        assertMatch(method, annotatedWith(Flag.class));
      }
    }, AnnotatedSelectorTest.class);
  }

  @Test
  public void testNotAnnotatedWithSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.notAnnotatedWith(Flag.class);
      }
      @Override
      public void assertions(Method method) {
        assertMatch(method, notAnnotatedWith(Flag.class));
      }
    }, AnnotatedSelectorTest.class);
  }

  @Test
  public void testNonFinalSelector() {
    Object obj = new Object(){
      void foo(){}
      final void bar(){}
    };
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.nonFinal();
      }
      public void assertions(Method method) {
        assertMatch(method, NON_FINAL);
      }
    }, obj);
  }

  static class NonStaticSelector {
    void foo(){}
    static void bar(){}
  }

  @Test
  public void testNonStaticSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.nonStatic();
      }
      public void assertions(Method method) {
        assertMatch(method, NON_STATIC);
      }
    }, NonStaticSelector.class);
  }

  static class RecursionTestObject {
    @Flag
    void foo(){};
  }

  @Test
  public void testRecursivelySelector() {
    assertNull(reflect().method().annotated().in(new RecursionTestObject(){}));
    assertNotNull(reflect().method().annotated().recursively().in(new RecursionTestObject(){}));
  }

  @Test
  public void testReturnTypeSelector() {
    Object obj = new Object(){
      void foo(){}
      int bar(){return 0;}
    };
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.withoutReturnType();
      }
      public void assertions(Method method) {
        assertEquals(Void.TYPE, method.getReturnType());
      }
    }, obj);
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.returning(int.class);
      }
      public void assertions(Method method) {
        assertEquals(int.class, method.getReturnType());
      }
    }, obj);
  }

  @Test
  public void testPredicateSelector() {
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.thatMatches(Predicates.ALWAYS_TRUE);
      }
    }, SimpleObject.class);
    assertNoResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.thatMatches(Predicates.ALWAYS_FALSE);
      }
    }, SimpleObject.class);
  }

  @Test
  public void testAccessSelector() {
    final Object obj = new Object(){
      private void privateMethod(){}
      void defaultMethod(){}
      protected void protectedMethod(){}
      public void publicMethod(){}
    };
    assertResult(reflect().method().withAccess(Access.PRIVATE).in(obj));
    assertResult(reflect().method().withAccess(Access.PROTECTED).in(obj));
    assertResult(reflect().method().withAccess(Access.DEFAULT).in(obj));
    assertResult(reflect().method().withAccess(Access.PUBLIC).in(obj));

    assertThrow(new Runnable(){
      public void run() {
        reflect().method().withAccess(Access.LIKE_DEFAULT).in(obj);
      }
    }, ReflectionException.class);
  }

  @Test
  public void testParameterSelector() throws Exception {
    Object obj = new Object(){
      void foo(boolean b){}
      void foo2(Boolean b){}
      void bar(boolean b, boolean bool){}
    };
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.withParameters(boolean.class);
      }
      public void assertions(Method method) {
        assertArrayEquals(new Class[]{boolean.class}, method.getParameterTypes());
      }
    }, obj);
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.withParameters(Boolean.class);
      }
    }, obj);
    assertResult(new SelectionTestAdapter<MethodSelector, Method>(){
      public MethodSelector createSelector() {
        return reflect().method();
      }
      public void makeSelections(MethodSelector selector) {
        selector.withParameters(boolean.class, boolean.class);
      }
      public void assertions(Method method) {
        assertArrayEquals(new Class[]{boolean.class, boolean.class}, method.getParameterTypes());
      }
    }, obj);
  }

}
