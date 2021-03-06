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

import io.backpackcloud.kodo.Spec;
import com.backpackcloud.trugger.HandlingException;
import com.backpackcloud.trugger.TruggerException;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static com.backpackcloud.trugger.element.ElementPredicates.readable;
import static com.backpackcloud.trugger.element.ElementPredicates.specific;
import static com.backpackcloud.trugger.element.ElementPredicates.writable;
import static com.backpackcloud.trugger.element.Elements.element;
import static com.backpackcloud.trugger.element.Elements.elements;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static io.backpackcloud.kodo.Expectation.it;
import static io.backpackcloud.kodo.Expectation.to;

/**
 * @author Marcelo Guimaraes
 */
public class ResultSetElementTest implements ElementExpectations {

  private ResultSet resultSet;

  @Before
  public void initialize() throws SQLException {
    resultSet = mock(ResultSet.class);

    ResultSetMetaData metadata = mock(ResultSetMetaData.class);
    when(metadata.getColumnCount()).thenReturn(3);
    when(metadata.getColumnName(1)).thenReturn("name");
    when(metadata.getColumnName(2)).thenReturn("nickname");
    when(metadata.getColumnName(3)).thenReturn("age");

    when(resultSet.getMetaData())
        .thenReturn(metadata);
    when(resultSet.getObject("name"))
        .thenReturn("John")
        .thenReturn("Justin")
        .thenThrow(new SQLException());
    when(resultSet.getObject(1))
        .thenReturn("John")
        .thenReturn("Justin")
        .thenThrow(new SQLException());
    when(resultSet.next()).thenReturn(true);
  }

  @Test
  public void testElements() {
    assertTrue(elements().from(ResultSet.class).isEmpty());

    Spec.given(elements().from(resultSet))
        .expect(it(), to().have(elementsNamed("name", "nickname", "age")))

        .each(Element.class, spec -> spec
            .expect(it(), to().be(specific())));
  }

  @Test(expected = TruggerException.class)
  public void testMetadataError() throws SQLException {
    when(resultSet.getMetaData()).thenThrow(new SQLException());

    elements().from(resultSet);
  }

  @Test
  public void testNamedElement() throws SQLException {
    Spec.given(element("name").from(ResultSet.class).get())
        .expect(it(), to().not().be(specific()));

    Spec.given(element("name").from(resultSet).get())
        .expect(Element::declaringClass, to().be(ResultSet.class))
        .expect(it(), to().be(readable()))
        .expect(it(), to().not().be(writable()))
        .expect(Element::getValue, to().be("John"))

        .when(retrievingNextRow())

        .expect(Element::getValue, to().be("Justin"))
        .expect(attempToChangeValue(), to().raise(HandlingException.class))
        .expect(gettingValueIn(new Object()), to().raise(HandlingException.class))
        .expect(gettingValue(), to().raise(HandlingException.class));
  }

  @Test
  public void testIndexedElement() throws SQLException {
    Spec.given(element("1").from(ResultSet.class).get())
        .expect(it(), to().not().be(specific()));

    Spec.given(element("1").from(resultSet).get())
        .expect(Element::declaringClass, to().be(ResultSet.class))
        .expect(it(), to().be(readable()))
        .expect(it(), to().not().be(writable()))
        .expect(Element::getValue, to().be("John"))

        .when(retrievingNextRow())

        .expect(Element::getValue, to().be("Justin"))
        .expect(attempToChangeValue(), to().raise(HandlingException.class))
        .expect(gettingValueIn(new Object()), to().raise(HandlingException.class))
        .expect(gettingValue(), to().raise(HandlingException.class));
  }

  private Runnable retrievingNextRow() {
    return () -> {
      try {
        resultSet.next();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    };
  }

}
