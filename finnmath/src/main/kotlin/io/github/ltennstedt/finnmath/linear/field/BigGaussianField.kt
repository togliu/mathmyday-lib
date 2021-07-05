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

import io.github.ltennstedt.finnmath.linear.vector.BigGaussianVector
import io.github.ltennstedt.finnmath.number.complex.BigComplex
import io.github.ltennstedt.finnmath.number.complex.BigGaussian

/**
 * Single implementation of a [Field] of [BigGaussians][BigGaussian]
 *
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public object BigGaussianField : Field<BigGaussian, BigComplex, BigGaussianVector> {
    override val addition: (a: BigGaussian, b: BigGaussian) -> BigGaussian
        get() = { a, b -> a + b }
    override val subtraction: (a: BigGaussian, b: BigGaussian) -> BigGaussian
        get() = { a, b -> a - b }
    override val multiplication: (a: BigGaussian, b: BigGaussian) -> BigGaussian
        get() = { a, b -> a * b }
    override val division: (a: BigGaussian, b: BigGaussian) -> BigComplex
        get() = { a, b -> a / b }
    override val negation: (e: BigGaussian) -> BigGaussian
        get() = { e -> -e }
    override val zero: BigGaussian
        get() = BigGaussian.ZERO
    override val one: BigGaussian
        get() = BigGaussian.ONE
    override val vectorConstructor: (m: Map<Int, BigGaussian>) -> BigGaussianVector
        get() = { m -> BigGaussianVector(m) }
}
