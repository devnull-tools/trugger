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
package org.atatec.trugger.scan.impl;

import org.atatec.trugger.registry.MapRegistry;
import org.atatec.trugger.registry.Registry;
import org.atatec.trugger.scan.ClassScanner;
import org.atatec.trugger.scan.ClassScannerFactory;
import org.atatec.trugger.scan.ClassScanningException;
import org.atatec.trugger.scan.ResourceFinder;

/**
 * The default class finder factory.
 *
 * @author Marcelo Varella Barca Guimarães
 */
public class TruggerClassScannerFactory implements ClassScannerFactory {

  private final Registry<String, ResourceFinder> registry = new MapRegistry<String, ResourceFinder>();

  public TruggerClassScannerFactory() {
    registry.register(new FileResourceFinder()).to(FileResourceFinder.PROTOCOL);
    registry.register(new JarResourceFinder()).to(JarResourceFinder.PROTOCOL);
    registry.register(new VfsFileResourceFinder()).to(VfsFileResourceFinder.PROTOCOL);
    registry.register(new VfsZipResourceFinder()).to(VfsZipResourceFinder.PROTOCOL);
  }

  public Registry<String, ResourceFinder> registry() {
    return registry;
  }

  public ResourceFinder finderFor(String protocol) {
    ResourceFinder finder = registry.registryFor(protocol.toLowerCase());
    if (finder == null) {
      throw new ClassScanningException("No finder registered for protocol \"" + protocol + '\"');
    }
    return finder;
  }

  /**
   * Returns a new instance of {@link TruggerClassScanner}.
   */
  public ClassScanner createClassScanner() {
    return new TruggerClassScanner(this);
  }

}