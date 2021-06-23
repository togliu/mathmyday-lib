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

import io.github.ltennstedt.finnmath.number.complex.BigComplex
import io.github.ltennstedt.finnmath.number.complex.BigGaussian

/**
 * Single implementation of the [BigGaussian] field
 *
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public object BigGaussianField : Field<BigGaussian, BigComplex> {
    override val addition: (a: BigGaussian, b: BigGaussian) -> BigGaussian
        get() = BigGaussian::add

    override val subtraction: (a: BigGaussian, b: BigGaussian) -> BigGaussian
        get() = BigGaussian::subtract

    override val multiplication: (a: BigGaussian, b: BigGaussian) -> BigGaussian
        get() = BigGaussian::multiply

    override val division: (a: BigGaussian, b: BigGaussian) -> BigComplex
        get() = BigGaussian::divide

    override val equalityByComparing: (a: BigGaussian, b: BigGaussian) -> Boolean
        get() = BigGaussian::equalsByComparing

    override val zero: BigGaussian
        get() = BigGaussian.ZERO
}
