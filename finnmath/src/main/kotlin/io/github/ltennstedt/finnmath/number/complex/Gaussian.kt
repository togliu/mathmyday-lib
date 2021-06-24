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
 * Immutable implementation of a gaussian number which uses [Long] as type for its real and imaginary part
 *
 * @property real real part
 * @property imaginary imaginary part
 * @constructor Constructs a Gaussian
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public class Gaussian @JvmOverloads constructor(
    real: Long,
    imaginary: Long = 0L
) : AbstractComplex<Long, Gaussian, Complex, Double, PolarForm>(
    real,
    imaginary
) {
    override val isUnit: Boolean by lazy { doesNotEqualByComparing(ZERO) }

    /**
     * Constructs a Gaussian from [real] and [imaginary]
     *
     * Default argument for [imaginary] is `0`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Int, imaginary: Int = 0) : this(real.toLong(), imaginary.toLong())

    override fun add(summand: Gaussian): Gaussian =
        Gaussian(real + summand.real, imaginary + summand.imaginary)

    override fun subtract(subtrahend: Gaussian): Gaussian = Gaussian(
        real - subtrahend.real,
        imaginary - subtrahend.imaginary
    )

    override fun multiply(factor: Gaussian): Gaussian {
        val newReal = real * factor.real - imaginary * factor.imaginary
        val newImaginary = real * factor.imaginary + imaginary * factor.real
        return Gaussian(newReal, newImaginary)
    }

    override fun divide(divisor: Gaussian): Complex {
        require(divisor.isUnit) { "expected divisor to be a unit but divisor = $divisor" }
        val d = divisor.real.toDouble().pow(2) + divisor.imaginary.toDouble().pow(2)
        val r = (real * divisor.real + imaginary * divisor.imaginary).toDouble() / d
        val i = (imaginary * divisor.real - real * divisor.imaginary).toDouble() / d
        return Complex(r, i)
    }

    override fun pow(exponent: Int): Complex = when {
        exponent < 0 -> toComplex().multiply(pow(-exponent - 1)).invert()
        exponent == 0 -> Complex.ONE
        else -> toComplex().multiply(pow(exponent - 1))
    }

    override fun negate(): Gaussian = Gaussian(-real, -imaginary)

    override fun invert(): Complex {
        check(isUnit) { "expected this to be a unit but this = $this" }
        return ONE.divide(this)
    }

    override fun absPow2(): Long = real * real + imaginary * imaginary

    override fun abs(): Double = sqrt(absPow2().toDouble())

    override fun conjugate(): Gaussian = Gaussian(real, -imaginary)

    override fun argument(): Double {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        val value = real.toDouble() / abs()
        val acos = acos(value)
        return if (imaginary < 0L) -acos else acos
    }

    override fun toPolarForm(): PolarForm {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        return PolarForm(abs(), argument())
    }

    /**
     * Returns the [BigPolarForm]
     *
     * @throws IllegalStateException if this is equal to 0 by comparing
     * @since 0.0.1
     */
    public fun toBigPolarForm(): BigPolarForm {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        return BigPolarForm(abs().toBigDecimal(), argument().toBigDecimal())
    }

    /**
     * Returns the [BigPolarForm] based on the [mathContext]
     *
     * @throws IllegalStateException if this is equal to 0 by comparing
     * @since 0.0.1
     */
    public fun toBigPolarForm(mathContext: MathContext): BigPolarForm {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        return BigPolarForm(abs().toBigDecimal(mathContext), argument().toBigDecimal(mathContext))
    }

    /**
     * Returns the [Complex]
     *
     * @since 0.0.1
     */
    public fun toComplex(): Complex = Complex(real, imaginary)

    /**
     * Returns the [BigGaussian]
     *
     * @since 0.0.1
     */
    public fun toBigGaussian(): BigGaussian = BigGaussian(real, imaginary)

    /**
     * Returns the [BigComplex]
     *
     * @since 0.0.1
     */
    public fun toBigComplex(): BigComplex = BigComplex(real, imaginary)

    override fun equalsByComparing(other: Gaussian): Boolean =
        real.compareTo(other.real) == 0 && imaginary.compareTo(other.imaginary) == 0

    public companion object {
        /**
         * `0` as Gaussian
         *
         * @since 0.0.1
         */
        @JvmField
        public val ZERO: Gaussian = Gaussian(0L)

        /**
         * `1` as Gaussian
         *
         * @since 0.0.1
         */
        @JvmField
        public val ONE: Gaussian = Gaussian(1L)

        /**
         * `i` as Gaussian
         *
         * @since 0.0.1
         */
        @JvmField
        public val IMAGINARY: Gaussian = Gaussian(0L, 1L)

        /**
         * `-1` as Gaussian
         *
         * @since 0.0.1
         */
        @JvmField
        public val MINUS_ONE: Gaussian = ONE.negate()

        /**
         * `-i` as Gaussian
         *
         * @since 0.0.1
         */
        @JvmField
        public val MINUS_IMAGINARY: Gaussian = IMAGINARY.negate()

        /**
         * Units
         *
         * @since 0.0.1
         */
        @JvmField
        public val UNITS: Set<Gaussian> = setOf(ONE, IMAGINARY, MINUS_ONE, MINUS_IMAGINARY)
    }
}
