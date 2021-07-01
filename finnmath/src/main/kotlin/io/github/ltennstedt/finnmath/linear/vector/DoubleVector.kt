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

package io.github.ltennstedt.finnmath.linear.vector

import com.google.common.annotations.Beta // ktlint-disable import-ordering
import io.github.ltennstedt.finnmath.linear.builder.DoubleVectorJavaBuilder
import kotlin.math.absoluteValue
import kotlin.math.sqrt
import org.apiguardian.api.API

@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public class DoubleVector(
    indexToElement: Map<Int, Double>
) : AbstractVector<Double, DoubleVector, Double, Double>(
    indexToElement
) {
    override fun add(summand: DoubleVector): DoubleVector {
        require(size == summand.size) { "Equal sizes expected but $size!=${summand.size}" }
        return DoubleVector(indexToElement.map { (i, e) -> i to (e + summand[i]) }.toMap())
    }

    override fun subtract(subtrahend: DoubleVector): DoubleVector {
        require(size == subtrahend.size) { "Equal sizes expected but $size!=${subtrahend.size}" }
        return DoubleVector(indexToElement.map { (i, e) -> i to (e - subtrahend[i]) }.toMap())
    }

    override fun dotProduct(other: DoubleVector): Double {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.map { (i, e) -> e * other[i] }.reduce { a, b -> a + b }
    }

    override fun scalarMultiply(scalar: Double): DoubleVector = DoubleVector(
        indexToElement.map { (i, e) -> i to (scalar * e) }.toMap()
    )

    override fun negate(): DoubleVector = DoubleVector(indexToElement.map { (i, e) -> i to (-e) }.toMap())

    override fun orthogonalTo(other: DoubleVector): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.map { (i, e) -> e * other[i] }.reduce { a, b -> a + b } == 0.0
    }

    override fun taxicabNorm(): Double = indexToElement.values.map { e -> e.absoluteValue }
        .reduce { a, b -> a + b }
        .toDouble()

    override fun euclideanNormPow2(): Double = this * this

    override fun euclideanNorm(): Double = sqrt(euclideanNormPow2())

    override fun maxNorm(): Double = indexToElement.values.map { it.absoluteValue }.maxOrNull() as Double

    override fun equalsByComparing(other: DoubleVector): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.all { (i, e) -> e.compareTo(other[i]) == 0 }
    }

    public companion object {
        /**
         * Returns a [DoubleVectorJavaBuilder] of [size]
         *
         * @since 0.0.1
         */
        @JvmStatic
        public fun builder(size: Int): DoubleVectorJavaBuilder = DoubleVectorJavaBuilder(size)
    }
}
