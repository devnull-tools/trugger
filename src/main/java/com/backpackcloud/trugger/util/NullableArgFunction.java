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

package com.backpackcloud.trugger.util;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An utility function that behaves differently if the supplied value is {@code null}.
 *
 * @since 7.0
 */
public class NullableArgFunction<T, R> implements Function<T, R> {

  private final Function<T, R> function;
  private final Supplier<R> supplier;

  /**
   * Creates a new NullableArgFunction based on the given functions.
   *
   * @param function the function to use for non-null values
   * @param supplier the supplier to use for null values
   */
  public NullableArgFunction(Function<T, R> function, Supplier<R> supplier) {
    this.function = function;
    this.supplier = supplier;
  }

  /**
   * Creates a new NullableArgFunction that will use the given function for null values.
   *
   * @param supplier the supplier to use for null values.
   * @return a new NullableArgFunction
   */
  public NullableArgFunction<T, R> orElse(Supplier<R> supplier) {
    return new NullableArgFunction<>(function, supplier);
  }

  /**
   * Creates a new NullableArgFunction that will use the given object as the return value
   * for null values.
   *
   * @param value the value to return when this function is called with a null value
   * @return a new NullableArgFunction
   */
  public NullableArgFunction<T, R> orElseReturn(R value) {
    return new NullableArgFunction<>(this.function, () -> value);
  }

  /**
   * Creates a new NullableArgFunction that will throw the exception supplied by the given
   * supplier for null values.
   *
   * @param exceptionSupplier the supplier to use when this function is called with a null value
   * @return a new NullableArgFunction
   */
  public NullableArgFunction<T, R> orElseThrow(Supplier<? extends RuntimeException> exceptionSupplier) {
    return new NullableArgFunction<>(this.function, () -> {
      throw exceptionSupplier.get();
    });
  }

  @Override
  public R apply(T t) {
    return t != null ? function.apply(t) : supplier.get();
  }

  /**
   * Creates a new NullableArgFunction in a fluent way. To define the alternative behavior,
   * use one of the orElse* methods.
   * <p>
   * The returned function will return {@code null} as the alternative behaviour.
   *
   * @param function the main function to use for non-null values.
   * @param <T>      the type of the input
   * @param <R>      the type of the result
   * @return a new NullableArgFunction
   */
  public static <T, R> NullableArgFunction<T, R> of(Function<T, R> function) {
    return new NullableArgFunction<>(function, () -> null);
  }

}
