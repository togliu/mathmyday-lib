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
import io.github.ltennstedt.finnmath.extension.toBigComplex
import io.github.ltennstedt.finnmath.linear.builder.BigDecimalVectorJavaBuilder
import io.github.ltennstedt.finnmath.linear.builder.bigComplexVector
import io.github.ltennstedt.finnmath.linear.field.BigDecimalField
import io.github.ltennstedt.finnmath.linear.field.Field
import org.apiguardian.api.API
import java.math.BigDecimal
import java.math.MathContext

/**
 * Immutable implementation of a vector whose elements are of type [BigDecimal]
 *
 * @property entries entries
 * @constructor Constructs an BigDecimalVector
 * @throws IllegalArgumentException if [entries] is empty
 * @throws IllegalArgumentException if [indices] `!= expectedIndices`
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
@Immutable
public class BigDecimalVector(
    entries: Set<VectorEntry<BigDecimal>>
) : AbstractVector<BigDecimal, BigDecimal, BigDecimalVector, BigDecimal, BigDecimal>(
    entries
) {
    override val field: Field<BigDecimal, BigDecimal, BigDecimalVector>
        get() = BigDecimalField

    /**
     * Returns the sum of this and the [summand] based on the [mathContext]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public fun add(summand: BigDecimalVector, mathContext: MathContext): BigDecimalVector {
        require(size == summand.size) { "Equal sizes expected but $size!=${summand.size}" }
        return BigDecimalVector(
            entries.sorted().map { (i, e) -> VectorEntry(i, e.add(summand[i], mathContext)) }.toSet()
        )
    }

    /**
     * Returns the difference of this and the [subtrahend] based on the [mathContext]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public fun subtract(subtrahend: BigDecimalVector, mathContext: MathContext): BigDecimalVector {
        require(size == subtrahend.size) { "Equal sizes expected but $size!=${subtrahend.size}" }
        return BigDecimalVector(
            entries.sorted().map { (i, e) -> VectorEntry(i, e.subtract(subtrahend[i], mathContext)) }.toSet()
        )
    }

    /**
     * Returns the dot product of this and the [other one][other] based on the [mathContext]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public fun dotProduct(other: BigDecimalVector, mathContext: MathContext): BigDecimal {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return entries.sorted()
            .map { (i, e) -> VectorEntry(i, e.multiply(other[i], mathContext)) }
            .map { it.element }
            .reduce { a, b -> a.add(b, mathContext) }
    }

    /**
     * Returns the scalar product of this and the [scalar] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun scalarMultiply(scalar: BigDecimal, mathContext: MathContext): BigDecimalVector = BigDecimalVector(
        entries.sorted().map { (i, e) -> VectorEntry(i, scalar.multiply(e, mathContext)) }.toSet()
    )

    /**
     * Returns the negated [AbstractVector] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun negate(mathContext: MathContext): BigDecimalVector =
        scalarMultiply(BigDecimal.ONE.negate(mathContext), mathContext)

    /**
     * Returns if this is orthogonal to [other] based on the [mathContext]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public fun orthogonalTo(other: BigDecimalVector, mathContext: MathContext): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return entries.map { (i, e) -> e.multiply(other[i], mathContext) }
            .reduce { a, b -> a.add(b, mathContext) }
            .compareTo(BigDecimal.ZERO) == 0
    }

    override fun taxicabNorm(): BigDecimal = elements.map { it.abs() }.reduce { a, b -> a + b }

    /**
     * Returns the taxicab norm based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun taxicabNorm(mathContext: MathContext): BigDecimal =
        elements.map { e -> e.abs(mathContext) }.reduce { a, b -> a.add(b, mathContext) }

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

    override fun maxNorm(): BigDecimal = elements.map { it.abs() }.maxOrNull() as BigDecimal

    /**
     * Returns the maximum norm based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun maxNorm(mathContext: MathContext): BigDecimal =
        elements.map { it.abs(mathContext) }.maxOrNull() as BigDecimal

    /**
     * Returns this as [BigDecimalVector]
     *
     * @since 0.0.1
     */
    public fun toBigDecimalVector(): BigComplexVector = bigComplexVector {
        computationOfAbsent = { this@BigDecimalVector[it].toBigComplex() }
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
