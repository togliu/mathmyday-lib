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

import io.github.ltennstedt.finnmath.linear.vector.DoubleVector
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry
import pw.forst.katlib.whenNull

/**
 * Provides DoubleVector block
 *
 * @since 0.0.1
 */
public fun doubleVector(init: DoubleVectorBuilder.() -> Unit): DoubleVector {
    val builder = DoubleVectorBuilder()
    builder.init()
    return builder.build()
}

/**
 * Builder for [DoubleVectors][DoubleVector]
 *
 * @constructor Constructs a DoubleVectorBuilder
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class DoubleVectorBuilder : AbstractVectorBuilder<Double, DoubleVector>() {
    override var computationOfAbsent: (Int) -> Double = { _ -> 0.0 }

    override fun build(): DoubleVector {
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
        return DoubleVector(entries.associate { (i, e) -> i to e })
    }
}