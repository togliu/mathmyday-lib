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

import io.github.ltennstedt.finnmath.linear.vector.LongVector

/**
 * Single implementation of a [Field] of [Longs][Long]
 *
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public object LongField : Field<Long, Double, LongVector> {
    override val addition: (a: Long, b: Long) -> Long
        get() = { a, b -> a + b }
    override val subtraction: (a: Long, b: Long) -> Long
        get() = { a, b -> a - b }
    override val multiplication: (a: Long, b: Long) -> Long
        get() = { a, b -> a * b }
    override val division: (a: Long, b: Long) -> Double
        get() = { a, b -> a.toDouble() / b.toDouble() }
    override val negation: (e: Long) -> Long
        get() = { e -> -e }
    override val equalityByComparing: (a: Long, b: Long) -> Boolean
        get() = { a, b -> a.compareTo(b) == 0 }
    override val zero: Long
        get() = 0L
    override val one: Long
        get() = 1L
    override val vectorConstructor: (m: Map<Int, Long>) -> LongVector
        get() = { LongVector(it) }
}
