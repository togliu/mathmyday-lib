/*
 * Copyright 2017 Lars Tennstedt
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

package io.github.ltennstedt.finnmath.number.complex

import com.google.common.annotations.Beta
import com.google.errorprone.annotations.Immutable
import io.github.ltennstedt.finnmath.extension.cos
import io.github.ltennstedt.finnmath.extension.sin
import org.apiguardian.api.API
import java.math.BigDecimal
import java.math.MathContext

/**
 * Immutable implementation of the polar form of a complex number number which uses [BigDecimal] as type for its
 * coordinates
 *
 * complexNumber = r * (cos(phi) + i * sin(phi))
 *
 * @property radial radial
 * @property angular angular
 * @constructor Constructs a BigPolarForm
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
@Immutable
public data class BigPolarForm(
    override val radial: BigDecimal,
    override val angular: BigDecimal
) : AbstractPolarForm<BigDecimal, BigPolarForm, BigComplex>(radial, angular) {
    /**
     * Constructs a BigPolarForm
     *
     * @since 0.0.1
     */
    public constructor(angular: Double, radial: Double) : this(radial.toBigDecimal(), angular.toBigDecimal())

    /**
     * Constructs a BigPolarForm based on the [mathContext]
     *
     * @since 0.0.1
     */
    public constructor(
        angular: Double,
        radial: Double,
        mathContext: MathContext
    ) : this(
        radial.toBigDecimal(mathContext),
        angular.toBigDecimal(mathContext)
    )

    override fun toComplexNumber(): BigComplex {
        val real = radial.multiply(angular.cos(MathContext.UNLIMITED), MathContext.UNLIMITED)
        val imaginary = radial.multiply(angular.sin(MathContext.UNLIMITED), MathContext.UNLIMITED)
        return BigComplex(real, imaginary)
    }

    /**
     * Returns this as [BigComplex] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun toComplexNumber(mathContext: MathContext): BigComplex {
        val real = radial.multiply(angular.cos(mathContext), mathContext)
        val imaginary = radial.multiply(angular.sin(mathContext), mathContext)
        return BigComplex(real, imaginary)
    }

    public companion object
}
