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

import io.github.ltennstedt.finnmath.linear.vector.ComplexVector
import io.github.ltennstedt.finnmath.number.complex.Complex

/**
 * Provides ComplexVector block
 *
 * @since 0.0.1
 */
public fun complexVector(init: ComplexVectorBuilder.() -> Unit): ComplexVector {
    val builder = ComplexVectorBuilder()
    builder.init()
    return builder.build()
}

/**
 * Builder for [ComplexVectors][ComplexVector]
 *
 * @constructor Constructs a ComplexVectorBuilder
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class ComplexVectorBuilder : AbstractVectorBuilder<Complex, ComplexVector>() {
    override var computationOfAbsent: (Int) -> Complex = { _ -> Complex.ZERO }
    override val vectorConstructor: (m: Map<Int, Complex>) -> ComplexVector
        get() = { ComplexVector(it) }
}
