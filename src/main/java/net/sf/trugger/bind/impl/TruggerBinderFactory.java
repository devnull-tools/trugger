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
package net.sf.trugger.bind.impl;

import net.sf.trugger.bind.Binder;
import net.sf.trugger.bind.BinderFactory;

/**
 * A default factory for creating {@link Binder} objects.
 * 
 * @author Marcelo Varella Barca Guimarães
 */
public class TruggerBinderFactory implements BinderFactory {
  
  /**
   * Returns a new {@link TruggerBinder}.
   */
  public Binder createBinder() {
    return new TruggerBinder();
  }
  
}
