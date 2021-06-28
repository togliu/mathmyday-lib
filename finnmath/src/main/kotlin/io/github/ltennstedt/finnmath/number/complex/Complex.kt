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

import com.google.common.annotations.Beta // ktlint-disable import-ordering
import java.math.MathContext
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt
import org.apiguardian.api.API

/**
 * Immutable implementation of a complex number which uses [Double] as type for its real and imaginary part
 *
 * @property real real part
 * @property imaginary imaginary part
 * @constructor Constructs a Complex
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public class Complex @JvmOverloads constructor(
    real: Double,
    imaginary: Double = 0.0
) : AbstractComplex<Double, Complex, Complex, Double, PolarForm>(
    real,
    imaginary
) {
    override val isUnit: Boolean by lazy { doesNotEqualByComparing(ZERO) }

    /**
     * Constructs a Complex from [real] and [imaginary]
     *
     * Default argument for [imaginary] is `0`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Int, imaginary: Int = 0) : this(real.toDouble(), imaginary.toDouble())

    /**
     * Constructs a Complex from [real] and [imaginary]
     *
     * Default argument for [imaginary] is `0`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Long, imaginary: Long = 0L) : this(real.toDouble(), imaginary.toDouble())

    /**
     * Constructs a Complex from [real] and [imaginary]
     *
     * Default argument for [imaginary] is `0.0F`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Float, imaginary: Float = 0.0F) : this(real.toDouble(), imaginary.toDouble())

    override fun add(summand: Complex): Complex = Complex(real + summand.real, imaginary + summand.imaginary)

    override fun subtract(subtrahend: Complex): Complex = Complex(
        real - subtrahend.real, imaginary - subtrahend.imaginary
    )

    override fun multiply(factor: Complex): Complex = Complex(
        real * factor.real - imaginary * factor.imaginary,
        real * factor.imaginary + imaginary * factor.real
    )

    override fun divide(divisor: Complex): Complex {
        require(divisor.isUnit) { "divisor expected to be a unit but divisor = $divisor" }
        val den = divisor.real.pow(2) + divisor.imaginary.pow(2)
        return Complex(
            (real * divisor.real + imaginary * divisor.imaginary) / den,
            (imaginary * divisor.real - real * divisor.imaginary) / den
        )
    }

    override fun pow(exponent: Int): Complex = when {
        exponent < 0 -> (this * pow(-exponent - 1)).invert()
        exponent == 0 -> ONE
        else -> this * pow(exponent - 1)
    }

    override fun negate(): Complex = Complex(-real, -imaginary)

    override fun invert(): Complex {
        check(isUnit) { "expected this to be a unit but this = $this" }
        return ONE / this
    }

    override fun absPow2(): Double = real.pow(2) + imaginary.pow(2)

    override fun abs(): Double = sqrt(absPow2())

    override fun conjugate(): Complex = Complex(real, -imaginary)

    override fun argument(): Double {
        check(doesNotEqualByComparing(ZERO)) { "this != 0 expected but this = $this" }
        val value = real / abs()
        val acos = acos(value)
        return if (imaginary < 0.0) -acos else acos
    }

    override fun toPolarForm(): PolarForm {
        check(doesNotEqualByComparing(ZERO)) { "this != 0 expected but this = $this" }
        return PolarForm(abs(), argument())
    }

    /**
     * Returns this as [BigPolarForm]
     *
     * @since 0.0.1
     */
    public fun toBigPolarForm(): BigPolarForm {
        check(doesNotEqualByComparing(ZERO)) { "this != 0 expected but this = $this" }
        return BigPolarForm(abs(), argument())
    }

    /**
     * Returns this as [BigPolarForm] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun toBigPolarForm(mathContext: MathContext): BigPolarForm {
        check(doesNotEqualByComparing(ZERO)) { "this != 0 expected but this = $this" }
        return BigPolarForm(abs(), argument(), mathContext)
    }

    /**
     * Returns this as [BigComplex]
     *
     * @since 0.0.1
     */
    public fun toBigComplex(): BigComplex = BigComplex(real, imaginary)

    /**
     * Returns this as [BigComplex] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun toBigComplex(mathContext: MathContext): BigComplex = BigComplex(real, imaginary, mathContext)

    override fun equalsByComparing(other: Complex): Boolean =
        real.compareTo(other.real) == 0 && imaginary.compareTo(other.imaginary) == 0

    public companion object {
        /**
         * `0` as Complex
         *
         * @since 0.0.1
         */
        @JvmField
        public val ZERO: Complex = Complex(0.0)

        /**
         * `1` as Complex
         *
         * @since 0.0.1
         */
        @JvmField
        public val ONE: Complex = Complex(1.0)

        /**
         * `i` as Complex
         *
         * @since 0.0.1
         */
        @JvmField
        public val IMAGINARY: Complex = Complex(0.0, 1.0)

        /**
         * `-1` as Complex
         *
         * @since 0.0.1
         */
        @JvmField
        public val MINUS_ONE: Complex = ONE.negate()

        /**
         * `-i` as Complex
         *
         * @since 0.0.1
         */
        @JvmField
        public val MINUS_IMAGINARY: Complex = IMAGINARY.negate()

        /**
         * Units
         *
         * @since 0.0.1
         */
        @JvmField
        public val UNITS: Set<Complex> = setOf(ONE, IMAGINARY, MINUS_ONE, MINUS_IMAGINARY)
    }
}
