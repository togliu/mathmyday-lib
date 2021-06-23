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
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Immutable implementation of a complex number which uses [Double] as type for its real and imaginary part
 *
 * @property real real part
 * @property imaginary imaginary part
 * @constructor Constructs a Complex
 * @author Lars Tennstedt
 * @since 0.0.1
 */
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

    override fun multiply(factor: Complex): Complex {
        val newReal = real * factor.real - imaginary * factor.imaginary
        val newImaginary = real * factor.imaginary + imaginary * factor.real
        return Complex(newReal, newImaginary)
    }

    override fun divide(divisor: Complex): Complex {
        require(divisor.isUnit) { "expected divisor to be a unit but divisor = $divisor" }
        val denominator = divisor.real.pow(2) + divisor.imaginary.pow(2)
        val newReal = (real * divisor.real + imaginary * divisor.imaginary) / denominator
        val newImaginary = (imaginary * divisor.real - real * divisor.imaginary) / denominator
        return Complex(newReal, newImaginary)
    }

    override fun pow(exponent: Int): Complex = when {
        exponent < 0 -> multiply(pow(-exponent - 1)).invert()
        exponent == 0 -> ONE
        else -> multiply(pow(exponent - 1))
    }

    override fun negate(): Complex = Complex(-real, -imaginary)

    override fun invert(): Complex {
        check(isUnit) { "expected this to be a unit but this = $this" }
        return ONE.divide(this)
    }

    override fun absPow2(): Double = real.pow(2) + imaginary.pow(2)

    override fun abs(): Double = sqrt(absPow2())

    override fun conjugate(): Complex = Complex(real, -imaginary)

    override fun argument(): Double {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        val value = real / abs()
        val acos = acos(value)
        return if (imaginary < 0.0) -acos else acos
    }

    override fun toPolarForm(): PolarForm {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        return PolarForm(abs(), argument())
    }

    /**
     * Returns the [BigPolarForm]
     *
     * @since 0.0.1
     */
    public fun toBigPolarForm(): BigPolarForm {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        return BigPolarForm(abs(), argument())
    }

    /**
     * Returns the [BigPolarForm] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun toBigPolarForm(mathContext: MathContext): BigPolarForm {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        return BigPolarForm(abs(), argument(), mathContext)
    }

    /**
     * Returns the [BigComplex]
     *
     * @since 0.0.1
     */
    public fun toBigComplex(): BigComplex = BigComplex(real, imaginary)

    /**
     * Returns the [BigComplex] based on the [mathContext]
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
