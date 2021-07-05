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
import io.github.ltennstedt.finnmath.extension.toBigComplex
import io.github.ltennstedt.finnmath.extension.toBigGaussian
import io.github.ltennstedt.finnmath.linear.builder.BigIntegerVectorJavaBuilder
import io.github.ltennstedt.finnmath.linear.builder.bigComplexVector
import io.github.ltennstedt.finnmath.linear.builder.bigDecimalVector
import io.github.ltennstedt.finnmath.linear.builder.bigGaussianVector
import io.github.ltennstedt.finnmath.linear.field.BigIntegerField
import org.apiguardian.api.API
import java.math.BigDecimal
import java.math.BigInteger

@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
@Immutable
public class BigIntegerVector(
    indexToElement: Map<Int, BigInteger>
) : AbstractVector<BigInteger, BigDecimal, BigIntegerVector, BigDecimal, BigInteger>(
    indexToElement,
    BigIntegerField
) {
    override fun taxicabNorm(): BigDecimal = elements.map(BigInteger::abs).reduce(BigInteger::add).toBigDecimal()

    /**
     * Returns the taxicab norm based on the [context]
     *
     * @since 0.0.1
     */
    public fun taxicabNorm(context: FinnmathContext): BigDecimal = elements.map(BigInteger::abs)
        .reduce(BigInteger::add)
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

    override fun maxNorm(): BigDecimal = elements.map { it.abs().toBigDecimal() }.maxOrNull() as BigDecimal

    /**
     * Returns the maximum norm based on the [context]
     *
     * @since 0.0.1
     */
    public fun maxNorm(context: FinnmathContext): BigDecimal = elements.map {
        it.abs().toBigDecimal(context.scale, context.mathContext)
    }.maxOrNull() as BigDecimal

    /**
     * Returns this as [BigDecimalVector]
     *
     * @since 0.0.1
     */
    public fun toBigDecimalVector(): BigDecimalVector = bigDecimalVector {
        computationOfAbsent = { this@BigIntegerVector[it].toBigDecimal() }
    }

    /**
     * Returns this as [BigDecimalVector] based on the [context]
     *
     * @since 0.0.1
     */
    public fun toBigDecimalVector(context: FinnmathContext): BigDecimalVector = bigDecimalVector {
        computationOfAbsent = { this@BigIntegerVector[it].toBigDecimal(context.scale, context.mathContext) }
    }

    /**
     * Returns this as [BigGaussianVector]
     *
     * @since 0.0.1
     */
    public fun toBigGaussianVector(): BigGaussianVector = bigGaussianVector {
        computationOfAbsent = { this@BigIntegerVector[it].toBigGaussian() }
    }

    /**
     * Returns this as [BigComplexVector]
     *
     * @since 0.0.1
     */
    public fun toBigComplexVector(): BigComplexVector = bigComplexVector {
        computationOfAbsent = { this@BigIntegerVector[it].toBigComplex() }
    }

    /**
     * Returns this as [BigComplexVector] vased on the [context]
     *
     * @since 0.0.1
     */
    public fun toBigComplexVector(context: FinnmathContext): BigComplexVector = bigComplexVector {
        computationOfAbsent = { this@BigIntegerVector[it].toBigComplex(context) }
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
