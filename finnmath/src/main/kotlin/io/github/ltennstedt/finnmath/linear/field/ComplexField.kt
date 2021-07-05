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

import io.github.ltennstedt.finnmath.linear.vector.ComplexVector
import io.github.ltennstedt.finnmath.number.complex.Complex

/**
 * Single implementation of a [Field] of [Complexes][Complex]
 *
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public object ComplexField : Field<Complex, Complex, ComplexVector> {
    override val addition: (a: Complex, b: Complex) -> Complex
        get() = Complex::add
    override val subtraction: (a: Complex, b: Complex) -> Complex
        get() = Complex::subtract
    override val multiplication: (a: Complex, b: Complex) -> Complex
        get() = Complex::multiply
    override val division: (a: Complex, b: Complex) -> Complex
        get() = Complex::divide
    override val negation: (e: Complex) -> Complex
        get() = Complex::negate
    override val equalityByComparing: (a: Complex, b: Complex) -> Boolean
        get() = Complex::equalsByComparing
    override val zero: Complex
        get() = Complex.ZERO
    override val one: Complex
        get() = Complex.ONE
    override val vectorConstructor: (m: Map<Int, Complex>) -> ComplexVector
        get() = { ComplexVector(it) }
}
