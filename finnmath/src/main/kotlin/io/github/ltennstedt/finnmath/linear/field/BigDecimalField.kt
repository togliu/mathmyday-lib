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

import io.github.ltennstedt.finnmath.linear.vector.BigDecimalVector
import java.math.BigDecimal

/**
 * Single implementation of a [Field] of [BigDecimals][BigDecimal]
 *
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public object BigDecimalField : Field<BigDecimal, BigDecimal, BigDecimalVector> {
    override val addition: (a: BigDecimal, b: BigDecimal) -> BigDecimal
        get() = BigDecimal::add
    override val subtraction: (a: BigDecimal, b: BigDecimal) -> BigDecimal
        get() = BigDecimal::subtract
    override val multiplication: (a: BigDecimal, b: BigDecimal) -> BigDecimal
        get() = BigDecimal::multiply
    override val division: (a: BigDecimal, b: BigDecimal) -> BigDecimal
        get() = BigDecimal::divide
    override val negation: (e: BigDecimal) -> BigDecimal
        get() = BigDecimal::negate
    override val equalityByComparing: (a: BigDecimal, b: BigDecimal) -> Boolean
        get() = { a, b -> a.compareTo(b) == 0 }
    override val zero: BigDecimal
        get() = BigDecimal.ZERO
    override val one: BigDecimal
        get() = BigDecimal.ONE
    override val vectorConstructor: (m: Map<Int, BigDecimal>) -> BigDecimalVector
        get() = { BigDecimalVector(it) }
}
