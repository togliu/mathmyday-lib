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

import io.github.ltennstedt.finnmath.linear.vector.GaussianVector
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry
import io.github.ltennstedt.finnmath.number.complex.Complex
import io.github.ltennstedt.finnmath.number.complex.Gaussian

/**
 * Single implementation of a [Field] of [Gaussians][Gaussian]
 *
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public object GaussianField : Field<Gaussian, Complex, GaussianVector> {
    override val addition: (a: Gaussian, b: Gaussian) -> Gaussian
        get() = Gaussian::add
    override val subtraction: (a: Gaussian, b: Gaussian) -> Gaussian
        get() = Gaussian::subtract
    override val multiplication: (a: Gaussian, b: Gaussian) -> Gaussian
        get() = Gaussian::multiply
    override val division: (a: Gaussian, b: Gaussian) -> Complex
        get() = Gaussian::divide
    override val negation: (e: Gaussian) -> Gaussian
        get() = Gaussian::negate
    override val equalityByComparing: (a: Gaussian, b: Gaussian) -> Boolean
        get() = Gaussian::equalsByComparing
    override val zero: Gaussian
        get() = Gaussian.ZERO
    override val one: Gaussian
        get() = Gaussian.ONE
    override val vectorConstructor: (s: Set<VectorEntry<Gaussian>>) -> GaussianVector
        get() = { GaussianVector(it) }
}
