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
import io.github.ltennstedt.finnmath.extension.toBigComplex
import io.github.ltennstedt.finnmath.extension.toBigGaussian
import io.github.ltennstedt.finnmath.extension.toComplex
import io.github.ltennstedt.finnmath.extension.toGaussian
import io.github.ltennstedt.finnmath.linear.builder.LongVectorJavaBuilder
import io.github.ltennstedt.finnmath.linear.builder.bigComplexVector
import io.github.ltennstedt.finnmath.linear.builder.bigDecimalVector
import io.github.ltennstedt.finnmath.linear.builder.bigGaussianVector
import io.github.ltennstedt.finnmath.linear.builder.complexVector
import io.github.ltennstedt.finnmath.linear.builder.doubleVector
import io.github.ltennstedt.finnmath.linear.builder.gaussianVector
import io.github.ltennstedt.finnmath.linear.field.LongField
import org.apiguardian.api.API
import java.math.MathContext
import kotlin.math.absoluteValue
import kotlin.math.sqrt

@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
@Immutable
public class LongVector(
    indexToElement: Map<Int, Long>,
) : AbstractVector<Long, Double, LongVector, Double, Long>(
    indexToElement,
    LongField
) {
    override fun taxicabNorm(): Double = elements.map(Long::absoluteValue).reduce { a, b -> a + b }.toDouble()

    override fun euclideanNormPow2(): Long = this * this

    override fun euclideanNorm(): Double = sqrt(euclideanNormPow2().toDouble())

    override fun maxNorm(): Double = elements.map { it.absoluteValue.toDouble() }.maxOrNull() as Double

    /**
     * Returns this as [DoubleVector]
     *
     * @since 0.0.1
     */
    public fun toDoubleVector(): DoubleVector = doubleVector {
        computationOfAbsent = { this@LongVector[it].toDouble() }
    }

    /**
     * Returns this as [BigIntegerVector]
     *
     * @since 0.0.1
     */
    public fun toBigIntegerVector(): BigIntegerVector =
        BigIntegerVector(indexToElement.map { (i, e) -> i to e.toBigInteger() }.toMap())

    /**
     * Returns this as [BigDecimalVector]
     *
     * @since 0.0.1
     */
    public fun toBigDecimalVector(): BigDecimalVector = bigDecimalVector {
        computationOfAbsent = { this@LongVector[it].toBigDecimal() }
    }

    /**
     * Returns this as [BigDecimalVector] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun toBigDecimalVector(mathContext: MathContext): BigDecimalVector = bigDecimalVector {
        computationOfAbsent = { this@LongVector[it].toBigDecimal(mathContext) }
    }

    /**
     * Returns this as [GaussianVector]
     *
     * @since 0.0.1
     */
    public fun toGaussianVector(): GaussianVector = gaussianVector {
        computationOfAbsent = { this@LongVector[it].toGaussian() }
    }

    /**
     * Returns this as [ComplexVector]
     *
     * @since 0.0.1
     */
    public fun toComplexVector(): ComplexVector = complexVector {
        computationOfAbsent = { this@LongVector[it].toComplex() }
    }

    /**
     * Returns this as [BigGaussianVector]
     *
     * @since 0.0.1
     */
    public fun toBigGaussianVector(): BigGaussianVector = bigGaussianVector {
        computationOfAbsent = { this@LongVector[it].toBigGaussian() }
    }

    /**
     * Returns this as [BigComplexVector]
     *
     * @since 0.0.1
     */
    public fun toBigComplexVector(): BigComplexVector = bigComplexVector {
        computationOfAbsent = { this@LongVector[it].toBigComplex() }
    }

    public companion object {
        /**
         * Returns a [LongVectorJavaBuilder] of [size]
         *
         * @since 0.0.1
         */
        @JvmStatic
        public fun builder(size: Int): LongVectorJavaBuilder = LongVectorJavaBuilder(size)
    }
}
