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

package io.github.ltennstedt.finnmath.number.complex

import java.math.MathContext
import kotlin.math.cos
import kotlin.math.sin

/**
 * Immutable implementation of the polar form of a complex number number which uses [Double] as type for its
 * coordinates
 *
 * complexNumber = r * (cos(phi) + i * sin(phi))
 *
 * @property radial radial
 * @property angular angular
 * @constructor Constructs a PolarForm
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public data class PolarForm(
    override val radial: Double,
    override val angular: Double
) : AbstractPolarForm<Double, PolarForm, Complex>(radial, angular) {
    /**
     * Returns the corresponding [Complex] of this
     *
     * @since 0.0.1
     */
    override fun toComplexNumber(): Complex = Complex(radial * cos(angular), radial * sin(angular))

    /**
     * Returns this as [BigPolarForm]
     *
     * @since 0.0.1
     */
    public fun toBigPolarForm(): BigPolarForm = BigPolarForm(radial.toBigDecimal(), angular.toBigDecimal())

    /**
     * Returns this as [BigPolarForm] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun toBigPolarForm(mathContext: MathContext): BigPolarForm = BigPolarForm(
        radial.toBigDecimal(mathContext),
        angular.toBigDecimal(mathContext)
    )

    public companion object
}
