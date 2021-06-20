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

package io.github.ltennstedt.finnmath.number

import io.github.ltennstedt.finnmath.extension.cos
import io.github.ltennstedt.finnmath.extension.sin
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
 * @constructor Constructs a [BigPolarForm]
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public data class BigPolarForm(val radial: BigDecimal, val angular: BigDecimal) {
    /**
     * Returns the corresponding [BigComplex] of [this][BigPolarForm] based on the [mathContext]
     *
     * @param mathContext [MathContext]; default argument is [MathContext.UNLIMITED]
     * @return [BigComplex]
     * @since 0.0.1
     */
    @JvmOverloads
    public fun toComplexNumber(mathContext: MathContext = MathContext.UNLIMITED): BigComplex {
        val real = radial.multiply(angular.cos(mathContext), mathContext)
        val imaginary = radial.multiply(angular.sin(mathContext), mathContext)
        return BigComplex(real, imaginary)
    }

    /**
     * Returns if [this][BigPolarForm] is equal to the [other one][other] by comparing coordinates
     *
     * @since 0.0.1
     */
    public fun equalsByComparing(other: BigPolarForm): Boolean =
        radial.compareTo(other.radial) == 0 && angular.compareTo(other.angular) == 0

    public companion object
}
