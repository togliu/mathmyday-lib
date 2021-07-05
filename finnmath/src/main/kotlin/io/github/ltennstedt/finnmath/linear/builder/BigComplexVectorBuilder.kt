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

import io.github.ltennstedt.finnmath.linear.vector.BigComplexVector
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry
import io.github.ltennstedt.finnmath.number.complex.BigComplex
import pw.forst.katlib.whenNull

/**
 * Provides BigComplexVector block
 *
 * @since 0.0.1
 */
public fun bigComplexVector(init: BigComplexVectorBuilder.() -> Unit): BigComplexVector {
    val builder = BigComplexVectorBuilder()
    builder.init()
    return builder.build()
}

/**
 * Builder for [BigComplexVectors][BigComplexVector]
 *
 * @constructor Constructs a BigComplexVectorBuilder
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class BigComplexVectorBuilder : AbstractVectorBuilder<BigComplex, BigComplexVector>() {
    override var computationOfAbsent: (Int) -> BigComplex = { _ -> BigComplex.ZERO }

    override fun build(): BigComplexVector {
        check(entries.isNotEmpty()) { "entries expected not to be empty but entries = $entries}" }
        val indices = entries.map { it.index }
        val maxIndex = indices.maxOrNull() as Int
        check(maxIndex <= size) { "maxIndex <= size expected but $maxIndex < $size" }
        val distinctIndices = indices.distinct()
        check(distinctIndices.size == indices.size) {
            "indices.distinct().size == indices.size expected but ${distinctIndices.size} != ${indices.size}"
        }
        for (i in 1..size) {
            entries.filter { it.index == i }.map { it.element }.singleOrNull().whenNull {
                entries.add(VectorEntry(i, computationOfAbsent(i)))
            }
        }
        return BigComplexVector(entries.associate { (i, e) -> i to e })
    }
}
