/*
 * Copyright 2009-2014 Marcelo Guimarães
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
package org.atatec.trugger.scan.impl;

import java.io.IOException;
import java.util.List;

/**
 * Interface that defines a scanner for a package entry.
 *
 * @author Marcelo Guimarães
 */
public interface Scanner {

  /**
   * Scans and returns the found classes in the specified package.
   *
   * @param packageName the package to scan.
   * @return the classes found in the package
   */
  List<Class> scan(String packageName) throws IOException,
      ClassNotFoundException;

  List<Class> deepScan(String packageName) throws IOException,
      ClassNotFoundException;

}
