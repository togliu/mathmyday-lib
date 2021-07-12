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
import io.github.ltennstedt.finnmath.extension.toComplex
import io.github.ltennstedt.finnmath.linear.builder.DoubleVectorJavaBuilder
import io.github.ltennstedt.finnmath.linear.builder.bigComplexVector
import io.github.ltennstedt.finnmath.linear.builder.bigDecimalVector
import io.github.ltennstedt.finnmath.linear.builder.complexVector
import io.github.ltennstedt.finnmath.linear.field.DoubleField
import io.github.ltennstedt.finnmath.linear.field.Field
import org.apiguardian.api.API
import java.math.MathContext
import kotlin.math.absoluteValue
import kotlin.math.sqrt

/**
 * Immutable implementation of a vector whose elements are of type [Double]
 *
 * @property entries entries
 * @constructor Constructs an BigComplexVector
 * @throws IllegalArgumentException if [entries] is empty
 * @throws IllegalArgumentException if [indices] `!= expectedIndices`
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
@Immutable
public class DoubleVector(
    entries: Set<VectorEntry<Double>>
) : AbstractVector<Double, Double, DoubleVector, Double, Double>(
    entries
) {
    override val field: Field<Double, Double, DoubleVector>
        get() = DoubleField

    override fun taxicabNorm(): Double = elements.map { it.absoluteValue }.reduce { a, b -> a + b }.toDouble()

    override fun euclideanNormPow2(): Double = this * this

    override fun euclideanNorm(): Double = sqrt(euclideanNormPow2())

    override fun maxNorm(): Double = elements.map { it.absoluteValue }.maxOrNull() as Double

    /**
     * Returns this as [BigDecimalVector]
     *
     * @since 0.0.1
     */
    public fun toBigDecimalVector(): BigDecimalVector = bigDecimalVector {
        computationOfAbsent = { this@DoubleVector[it].toBigDecimal() }
    }

    /**
     * Returns this as [BigDecimalVector] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun toBigDecimalVector(mathContext: MathContext): BigDecimalVector = bigDecimalVector {
        computationOfAbsent = { this@DoubleVector[it].toBigDecimal(mathContext) }
    }

    /**
     * Returns this as [ComplexVector]
     *
     * @since 0.0.1
     */
    public fun toComplexVector(): ComplexVector = complexVector {
        computationOfAbsent = { this@DoubleVector[it].toComplex() }
    }

    /**
     * Returns this as [BigComplexVector]
     *
     * @since 0.0.1
     */
    public fun toBigComplexVector(): BigComplexVector = bigComplexVector {
        computationOfAbsent = { this@DoubleVector[it].toBigComplex() }
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
