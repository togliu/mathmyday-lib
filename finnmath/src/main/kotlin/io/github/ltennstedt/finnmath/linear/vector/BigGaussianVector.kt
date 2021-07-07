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
import io.github.ltennstedt.finnmath.linear.builder.bigComplexVector
import io.github.ltennstedt.finnmath.linear.field.BigGaussianField
import io.github.ltennstedt.finnmath.linear.field.Field
import io.github.ltennstedt.finnmath.number.complex.BigComplex
import io.github.ltennstedt.finnmath.number.complex.BigGaussian
import org.apiguardian.api.API
import java.math.BigDecimal
import java.math.MathContext

/**
 * Immutable implementation of a vector whose elements are of type [BigGaussian]
 *
 * @property indexToElement [Map]
 * @constructor Constructs an BigComplexVector
 * @throws IllegalArgumentException if [indexToElement] is empty
 * @throws IllegalArgumentException if [indices] `!= expectedIndices`
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
@Immutable
public class BigGaussianVector(
    indexToElement: Map<Int, BigGaussian>
) : AbstractVector<BigGaussian, BigComplex, BigGaussianVector, BigDecimal, BigGaussian>(
    indexToElement
) {
    override val field: Field<BigGaussian, BigComplex, BigGaussianVector>
        get() = BigGaussianField

    override fun taxicabNorm(): BigDecimal = elements.map(BigGaussian::abs).reduce(BigDecimal::add)

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

    override fun euclideanNorm(): BigDecimal = elements.map { it.abs().pow(2) }.reduce(BigDecimal::add).sqrt()

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

    override fun maxNorm(): BigDecimal = elements.map(BigGaussian::abs).maxOrNull() as BigDecimal

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

    /**
     * Returns this as [BigComplexVector]
     *
     * @since 0.0.1
     */
    public fun toBigComplexVector(): BigComplexVector = bigComplexVector {
        computationOfAbsent = { this@BigGaussianVector[it].toBigComplex() }
    }

    /**
     * Returns this as [BigComplexVector] vased on the [context]
     *
     * @since 0.0.1
     */
    public fun toBigComplexVector(context: FinnmathContext): BigComplexVector = bigComplexVector {
        computationOfAbsent = { this@BigGaussianVector[it].toBigComplex(context) }
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
