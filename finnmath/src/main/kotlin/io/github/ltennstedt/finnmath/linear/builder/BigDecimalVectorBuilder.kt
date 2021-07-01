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

import io.github.ltennstedt.finnmath.linear.vector.BigDecimalVector // ktlint-disable import-ordering
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry // ktlint-disable import-ordering
import java.math.BigDecimal
import pw.forst.katlib.whenNull

/**
 * Provides BigDecimalVector block
 *
 * @since 0.0.1
 */
public fun bigDecimalVector(init: BigDecimalVectorBuilder.() -> Unit): BigDecimalVector {
    val builder = BigDecimalVectorBuilder()
    builder.init()
    return builder.build()
}

/**
 * Builder for [BigDecimalVectors][BigDecimalVector]
 *
 * @constructor Constructs a BigDecimalVectorBuilder
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class BigDecimalVectorBuilder : AbstractVectorBuilder<BigDecimal, BigDecimalVector>() {
    override var computationOfAbsent: (Int) -> BigDecimal = { _ -> BigDecimal.ZERO }

    override fun build(): BigDecimalVector {
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
        return BigDecimalVector(entries.associate { (i, e) -> i to e })
    }
}
