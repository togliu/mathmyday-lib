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
import io.github.ltennstedt.finnmath.linear.builder.ComplexVectorJavaBuilder
import io.github.ltennstedt.finnmath.linear.builder.bigComplexVector
import io.github.ltennstedt.finnmath.linear.field.ComplexField
import io.github.ltennstedt.finnmath.number.complex.Complex
import org.apiguardian.api.API
import kotlin.math.pow
import kotlin.math.sqrt

@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
@Immutable
public class ComplexVector(
    indexToElement: Map<Int, Complex>
) : AbstractVector<Complex, Complex, ComplexVector, Double, Complex>(
    indexToElement,
    ComplexField
) {
    override fun taxicabNorm(): Double = elements.map(Complex::abs).reduce { a, b -> a + b }

    override fun euclideanNormPow2(): Complex = this * this

    override fun euclideanNorm(): Double = sqrt(elements.map { it.abs().pow(2) }.reduce { a, b -> a + b })

    override fun maxNorm(): Double = elements.map(Complex::abs).maxOrNull() as Double

    /**
     * Returns this as [BigComplexVector]
     *
     * @since 0.0.1
     */
    public fun toBigComplexVector(): BigComplexVector = bigComplexVector {
        computationOfAbsent = { this@ComplexVector[it].toBigComplex() }
    }

    public companion object {
        /**
         * Returns a [ComplexVectorJavaBuilder] of [size]
         *
         * @since 0.0.1
         */
        @JvmStatic
        public fun builder(size: Int): ComplexVectorJavaBuilder = ComplexVectorJavaBuilder(size)
    }
}
