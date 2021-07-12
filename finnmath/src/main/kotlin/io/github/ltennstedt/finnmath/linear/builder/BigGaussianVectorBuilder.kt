/*
 * Copyright 2020 Lars Tennstedt
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

package io.github.ltennstedt.finnmath.linear.builder

import io.github.ltennstedt.finnmath.linear.field.BigGaussianField
import io.github.ltennstedt.finnmath.linear.vector.BigGaussianVector
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry
import io.github.ltennstedt.finnmath.number.complex.BigGaussian

/**
 * Provides BigGaussianVector block
 *
 * @since 0.0.1
 */
public fun bigGaussianVector(init: BigGaussianVectorBuilder.() -> Unit): BigGaussianVector {
    val builder = BigGaussianVectorBuilder()
    builder.init()
    return builder.build()
}

/**
 * Builder for [BigGaussianVectors][BigGaussianVector]
 *
 * @constructor Constructs a BigGaussianVectorBuilder
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class BigGaussianVectorBuilder : AbstractVectorBuilder<BigGaussian, BigGaussianVector>() {
    override var computationOfAbsent: (Int) -> BigGaussian = { _ -> BigGaussian.ZERO }
    override val vectorConstructor: (s: Set<VectorEntry<BigGaussian>>) -> BigGaussianVector
        get() = BigGaussianField.vectorConstructor
}
