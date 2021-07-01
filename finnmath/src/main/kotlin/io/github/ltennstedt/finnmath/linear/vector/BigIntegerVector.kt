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
import io.github.ltennstedt.finnmath.FinnmathContext
import io.github.ltennstedt.finnmath.extension.sqrt
import io.github.ltennstedt.finnmath.linear.builder.BigIntegerVectorJavaBuilder
import java.math.BigDecimal
import java.math.BigInteger
import org.apiguardian.api.API

@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public class BigIntegerVector(
    indexToElement: Map<Int, BigInteger>
) : AbstractVector<BigInteger, BigIntegerVector, BigDecimal, BigInteger>(
    indexToElement
) {
    override fun add(summand: BigIntegerVector): BigIntegerVector {
        require(size == summand.size) { "Equal sizes expected but $size!=${summand.size}" }
        return BigIntegerVector(indexToElement.map { (i, e) -> i to (e + summand[i]) }.toMap())
    }

    override fun subtract(subtrahend: BigIntegerVector): BigIntegerVector {
        require(size == subtrahend.size) { "Equal sizes expected but $size!=${subtrahend.size}" }
        return BigIntegerVector(indexToElement.map { (i, e) -> i to (e - subtrahend[i]) }.toMap())
    }

    override fun dotProduct(other: BigIntegerVector): BigInteger {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.map { (i, e) -> e * other[i] }.reduce { a, b -> a + b }
    }

    override fun scalarMultiply(scalar: BigInteger): BigIntegerVector = BigIntegerVector(
        indexToElement.map { (i, e) -> i to (scalar * e) }.toMap()
    )

    override fun negate(): BigIntegerVector = BigIntegerVector(indexToElement.map { (i, e) -> i to (-e) }.toMap())

    override fun orthogonalTo(other: BigIntegerVector): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.map { (i, e) -> e * other[i] }.reduce { a, b -> a + b } == BigInteger.ZERO
    }

    override fun taxicabNorm(): BigDecimal = indexToElement.values.map { e -> e.abs() }
        .reduce { a, b -> a + b }
        .toBigDecimal()

    /**
     * Returns the taxicab norm based on the [context]
     *
     * @since 0.0.1
     */
    public fun taxicabNorm(context: FinnmathContext): BigDecimal = indexToElement.values.map { e -> e.abs() }
        .reduce { a, b -> a + b }
        .toBigDecimal(context.scale, context.mathContext)

    override fun euclideanNormPow2(): BigInteger = this * this

    override fun euclideanNorm(): BigDecimal = euclideanNormPow2().toBigDecimal().sqrt()

    /**
     * Returns the euclidean norm based on the [context]
     *
     * @since 0.0.1
     */
    public fun euclideanNorm(context: FinnmathContext): BigDecimal =
        euclideanNormPow2().toBigDecimal(context.scale, context.mathContext).sqrt(context.mathContext)

    override fun maxNorm(): BigDecimal = indexToElement.values.map { it.abs().toBigDecimal() }.maxOrNull() as BigDecimal

    /**
     * Returns the maximum norm based on the [context]
     *
     * @since 0.0.1
     */
    public fun maxNorm(context: FinnmathContext): BigDecimal = indexToElement.values.map {
        it.abs().toBigDecimal(context.scale, context.mathContext)
    }.maxOrNull() as BigDecimal

    override fun equalsByComparing(other: BigIntegerVector): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.all { (i, e) -> e.compareTo(other[i]) == 0 }
    }

    public companion object {
        /**
         * Returns a [BigIntegerVectorJavaBuilder] of [size]
         *
         * @since 0.0.1
         */
        @JvmStatic
        public fun builder(size: Int): BigIntegerVectorJavaBuilder = BigIntegerVectorJavaBuilder(size)
    }
}
