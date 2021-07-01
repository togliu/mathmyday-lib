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
import io.github.ltennstedt.finnmath.extension.sqrt
import io.github.ltennstedt.finnmath.linear.builder.BigDecimalVectorJavaBuilder
import java.math.BigDecimal
import java.math.MathContext
import org.apiguardian.api.API

@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
@Immutable
public class BigDecimalVector(
    indexToElement: Map<Int, BigDecimal>
) : AbstractVector<BigDecimal, BigDecimalVector, BigDecimal, BigDecimal>(
    indexToElement
) {
    override fun add(summand: BigDecimalVector): BigDecimalVector {
        require(size == summand.size) { "Equal sizes expected but $size!=${summand.size}" }
        return BigDecimalVector(indexToElement.map { (i, e) -> i to (e + summand[i]) }.toMap())
    }

    /**
     * Returns the sum of this and the [summand] based on the [mathContext]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public fun add(summand: BigDecimalVector, mathContext: MathContext): BigDecimalVector {
        require(size == summand.size) { "Equal sizes expected but $size!=${summand.size}" }
        return BigDecimalVector(indexToElement.map { (i, e) -> i to e.add(summand[i], mathContext) }.toMap())
    }

    override fun subtract(subtrahend: BigDecimalVector): BigDecimalVector {
        require(size == subtrahend.size) { "Equal sizes expected but $size!=${subtrahend.size}" }
        return BigDecimalVector(indexToElement.map { (i, e) -> i to (e - subtrahend[i]) }.toMap())
    }

    /**
     * Returns the difference of this and the [subtrahend] based on the [mathContext]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public fun subtract(subtrahend: BigDecimalVector, mathContext: MathContext): BigDecimalVector {
        require(size == subtrahend.size) { "Equal sizes expected but $size!=${subtrahend.size}" }
        return BigDecimalVector(indexToElement.map { (i, e) -> i to e.subtract(subtrahend[i], mathContext) }.toMap())
    }

    override fun dotProduct(other: BigDecimalVector): BigDecimal {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.map { (i, e) -> e * other[i] }.reduce { a, b -> a + b }
    }

    /**
     * Returns the dot product of this and the [other one][other] based on the [mathContext]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public fun dotProduct(other: BigDecimalVector, mathContext: MathContext): BigDecimal {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.map { (i, e) -> e.multiply(other[i], mathContext) }
            .reduce { a, b -> a.add(b, mathContext) }
    }

    override fun scalarMultiply(scalar: BigDecimal): BigDecimalVector = BigDecimalVector(
        indexToElement.map { (i, e) -> i to (scalar * e) }.toMap()
    )

    /**
     * Returns the scalar product of this and the [scalar] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun scalarMultiply(scalar: BigDecimal, mathContext: MathContext): BigDecimalVector = BigDecimalVector(
        indexToElement.map { (i, e) -> i to scalar.multiply(e, mathContext) }.toMap()
    )

    override fun negate(): BigDecimalVector = BigDecimalVector(indexToElement.map { (i, e) -> i to (-e) }.toMap())

    /**
     * Returns the negated [AbstractVector] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun negate(mathContext: MathContext): BigDecimalVector =
        BigDecimalVector(indexToElement.map { (i, e) -> i to e.negate(mathContext) }.toMap())

    override fun orthogonalTo(other: BigDecimalVector): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.map { (i, e) -> e * other[i] }.reduce { a, b -> a + b } == BigDecimal.ZERO
    }

    /**
     * Returns if this is orthogonal to [other] based on the [mathContext]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public fun orthogonalTo(other: BigDecimalVector, mathContext: MathContext): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.map { (i, e) -> e.multiply(other[i], mathContext) }
            .reduce { a, b -> a.add(b, mathContext) }
            .compareTo(BigDecimal.ZERO) == 0
    }

    override fun taxicabNorm(): BigDecimal = indexToElement.values.map { e -> e.abs() }.reduce { a, b -> a + b }

    /**
     * Returns the taxicab norm based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun taxicabNorm(mathContext: MathContext): BigDecimal =
        indexToElement.values.map { e -> e.abs(mathContext) }.reduce { a, b -> a.add(b, mathContext) }

    override fun euclideanNormPow2(): BigDecimal = this * this

    /**
     * Returns the square of the euclidean norm based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun euclideanNormPow2(mathContext: MathContext): BigDecimal = dotProduct(this, mathContext)

    override fun euclideanNorm(): BigDecimal = euclideanNormPow2().sqrt()

    /**
     * Returns the euclidean norm based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun euclideanNorm(mathContext: MathContext): BigDecimal = euclideanNormPow2(mathContext).sqrt(mathContext)

    override fun maxNorm(): BigDecimal = indexToElement.values.map { it.abs() }.maxOrNull() as BigDecimal

    /**
     * Returns the maximum norm based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun maxNorm(mathContext: MathContext): BigDecimal =
        indexToElement.values.map { it.abs(mathContext) }.maxOrNull() as BigDecimal

    override fun equalsByComparing(other: BigDecimalVector): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return indexToElement.all { (i, e) -> e.compareTo(other[i]) == 0 }
    }

    public companion object {
        /**
         * Returns a [BigDecimalVectorJavaBuilder] of [size]
         *
         * @since 0.0.1
         */
        @JvmStatic
        public fun builder(size: Int): BigDecimalVectorJavaBuilder = BigDecimalVectorJavaBuilder(size)
    }
}
