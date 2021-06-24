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

import io.github.ltennstedt.finnmath.linear.vector.LongVector
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry
import pw.forst.katlib.whenNull

/**
 * Provides longVector block
 *
 * @since 0.0.1
 */
public fun longVector(init: LongVectorBuilder.() -> Unit): LongVector {
    val builder = LongVectorBuilder()
    builder.init()
    return builder.build()
}

/**
 * Builder for [LongVectors][LongVector]
 *
 * @constructor Constructs a LongVectorBuilder
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class LongVectorBuilder : AbstractVectorBuilder<Long, LongVector>() {
    override var computationForAbsent: (Int) -> Long = { _ -> 0L }

    override fun build(): LongVector {
        check(entries.isNotEmpty()) {
            "expected entries not to be empty but entries.isNotEmpty() = ${entries.isNotEmpty()}"
        }
        val indices = entries.map { it.index }
        val maxIndex = indices.maxOrNull() as Int
        check(maxIndex <= size) { "expected maxIndex <= size but maxIndex > $maxIndex" }
        val distinctIndices = indices.distinct()
        check(distinctIndices.size == indices.size) {
            "expected indices.distinct().size == indices.size but ${distinctIndices.size} != ${indices.size}"
        }
        for (i in 1..size) {
            entries.filter { it.index == i }.map { it.element }.singleOrNull().whenNull {
                entries.add(VectorEntry(i, computationForAbsent(i)))
            }
        }
        return LongVector(entries.associate { (i, e) -> i to e })
    }
}
