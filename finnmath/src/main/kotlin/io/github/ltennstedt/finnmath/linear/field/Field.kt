/*
 * Copyright 2021 Lars Tennstedt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ltennstedt.finnmath.linear.field

import io.github.ltennstedt.finnmath.linear.vector.AbstractVector
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry

/**
 * Interface for fields
 *
 * @param E type of elements
 * @param Q type of quotient
 * @param V type of vector
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public interface Field<E : Number, Q : Number, V : AbstractVector<E, Q, V, *, *>> {
    /**
     * Addition
     *
     * @since 0.0.1
     */
    public val addition: (a: E, b: E) -> E

    /**
     * Subtraction
     *
     * @since 0.0.1
     */
    public val subtraction: (a: E, b: E) -> E

    /**
     * Multiplication
     *
     * @since 0.0.1
     */
    public val multiplication: (a: E, b: E) -> E

    /**
     * Division
     *
     * @since 0.0.1
     */
    public val division: (a: E, b: E) -> Q

    /**
     * Negation
     *
     * @since 0.0.1
     */
    public val negation: (e: E) -> E

    /**
     * Equality by comparing
     *
     * @since 0.0.1
     */
    public val equalityByComparing: (a: E, b: E) -> Boolean

    /**
     * 0
     *
     * @since 0.0.1
     */
    public val zero: E

    /**
     * 1
     *
     * @since 0.0.1
     */
    public val one: E

    /**
     * Vector constructor
     *
     * @since 0.0.1
     */
    public val vectorConstructor: (s: Set<VectorEntry<E>>) -> V
}
