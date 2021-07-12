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

import io.github.ltennstedt.finnmath.linear.field.GaussianField
import io.github.ltennstedt.finnmath.linear.vector.GaussianVector
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry
import io.github.ltennstedt.finnmath.number.complex.Gaussian

/**
 * Provides GaussianVector block
 *
 * @since 0.0.1
 */
public fun gaussianVector(init: GaussianVectorBuilder.() -> Unit): GaussianVector {
    val builder = GaussianVectorBuilder()
    builder.init()
    return builder.build()
}

/**
 * Builder for [GaussianVectors][GaussianVector]
 *
 * @constructor Constructs a GaussianVectorBuilder
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class GaussianVectorBuilder : AbstractVectorBuilder<Gaussian, GaussianVector>() {
    override var computationOfAbsent: (Int) -> Gaussian = { _ -> Gaussian.ZERO }
    override val vectorConstructor: (s: Set<VectorEntry<Gaussian>>) -> GaussianVector
        get() = GaussianField.vectorConstructor
}
