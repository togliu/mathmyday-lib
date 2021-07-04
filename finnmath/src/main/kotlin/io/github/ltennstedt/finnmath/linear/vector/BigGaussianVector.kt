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

import com.google.common.annotations.Beta
import com.google.errorprone.annotations.Immutable
import io.github.ltennstedt.finnmath.FinnmathContext
import io.github.ltennstedt.finnmath.extension.sqrt
import io.github.ltennstedt.finnmath.linear.builder.BigGaussianVectorJavaBuilder
import io.github.ltennstedt.finnmath.number.complex.BigGaussian
import org.apiguardian.api.API
import java.math.BigDecimal
import java.math.MathContext

@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
@Immutable
public class BigGaussianVector(
    indexToElement: Map<Int, BigGaussian>
) : AbstractVector<BigGaussian, BigGaussianVector, BigDecimal, BigGaussian>(
    indexToElement
) {
    override fun add(summand: BigGaussianVector): BigGaussianVector {
        require(size == summand.size) { "Equal sizes expected but $size!=${summand.size}" }
        return BigGaussianVector(indexToElement.map { (i, e) -> i to (e + summand[i]) }.toMap())
    }

    override fun subtract(subtrahend: BigGaussianVector): BigGaussianVector {
        require(size == subtrahend.size) { "Equal sizes expected but $size!=${subtrahend.size}" }
        return BigGaussianVector(indexToElement.map { (i, e) -> i to (e - subtrahend[i]) }.toMap())
    }

    override fun dotProduct(other: BigGaussianVector): BigGaussian {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.map { (i, e) -> e * other[i] }.reduce { a, b -> a + b }
    }

    override fun scalarMultiply(scalar: BigGaussian): BigGaussianVector = BigGaussianVector(
        indexToElement.map { (i, e) -> i to (scalar * e) }.toMap()
    )

    override fun negate(): BigGaussianVector = BigGaussianVector(indexToElement.map { (i, e) -> i to (-e) }.toMap())

    override fun orthogonalTo(other: BigGaussianVector): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.map { (i, e) -> e * other[i] }.reduce { a, b -> a + b } == BigGaussian.ZERO
    }

    override fun taxicabNorm(): BigDecimal = elements.map { it.abs() }.reduce { a, b -> a + b }

    /**
     * Returns the taxicab norm based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun taxicabNorm(mathContext: MathContext): BigDecimal = elements.map { it.abs(mathContext) }
        .reduce { a, b -> a.add(b, mathContext) }

    /**
     * Returns the taxicab norm based on the [context]
     *
     * @since 0.0.1
     */
    public fun taxicabNorm(context: FinnmathContext): BigDecimal = elements.map { it.abs(context) }
        .reduce { a, b -> a.add(b, context.mathContext) }

    override fun euclideanNormPow2(): BigGaussian = this * this

    override fun euclideanNorm(): BigDecimal = elements.map { it.abs().pow(2) }
        .reduce { a, b -> a + b }
        .sqrt()

    /**
     * Returns the euclidean norm based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun euclideanNorm(mathContext: MathContext): BigDecimal = elements.map { it.abs(mathContext).pow(2) }
        .reduce { a, b -> a.add(b, mathContext) }
        .sqrt(mathContext)

    /**
     * Returns the euclidean norm based on the [context]
     *
     * @since 0.0.1
     */
    public fun euclideanNorm(context: FinnmathContext): BigDecimal = elements.map { it.abs(context).pow(2) }
        .reduce { a, b -> a.add(b, context.mathContext) }
        .sqrt(context.mathContext)

    override fun maxNorm(): BigDecimal = elements.map { it.abs() }.maxOrNull() as BigDecimal

    /**
     * Returns the maximum norm based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun maxNorm(mathContext: MathContext): BigDecimal =
        elements.map { it.abs(mathContext) }.maxOrNull() as BigDecimal

    /**
     * Returns the maximum norm based on the [context]
     *
     * @since 0.0.1
     */
    public fun maxNorm(context: FinnmathContext): BigDecimal =
        elements.map { it.abs(context) }.maxOrNull() as BigDecimal

    override fun equalsByComparing(other: BigGaussianVector): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.all { (i, e) -> e.equalsByComparing(other[i]) }
    }

    public companion object {
        /**
         * Returns a [BigGaussianVectorJavaBuilder] of [size]
         *
         * @since 0.0.1
         */
        @JvmStatic
        public fun builder(size: Int): BigGaussianVectorJavaBuilder = BigGaussianVectorJavaBuilder(size)
    }
}
