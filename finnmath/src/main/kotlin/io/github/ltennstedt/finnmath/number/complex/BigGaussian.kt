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
import io.github.ltennstedt.finnmath.FinnmathContext
import io.github.ltennstedt.finnmath.extension.acos
import io.github.ltennstedt.finnmath.extension.sqrt
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import org.apiguardian.api.API

/**
 * Immutable implementation of a gaussian number which uses [BigInteger] as type for its real and imaginary part
 *
 * @property real real part
 * @property imaginary imaginary part
 * @constructor Constructs a BigGaussian
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public class BigGaussian @JvmOverloads constructor(
    real: BigInteger,
    imaginary: BigInteger = BigInteger.ZERO
) : AbstractComplex<BigInteger, BigGaussian, BigComplex, BigDecimal, BigPolarForm>(
    real,
    imaginary
) {
    override val isUnit: Boolean by lazy { doesNotEqualByComparing(ZERO) }

    /**
     * Constructs a BigGaussian from [real] and [imaginary]
     *
     * Default argument for [imaginary] is `0`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Int, imaginary: Int = 0) : this(real.toBigInteger(), imaginary.toBigInteger())

    /**
     * Constructs a BigGaussian from [real] and [imaginary]
     *
     * Default argument for [imaginary] is `0`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Long, imaginary: Long = 0L) : this(real.toBigInteger(), imaginary.toBigInteger())

    override fun add(summand: BigGaussian): BigGaussian =
        BigGaussian(real + summand.real, imaginary + summand.imaginary)

    override fun subtract(subtrahend: BigGaussian): BigGaussian = BigGaussian(
        real - subtrahend.real,
        imaginary - subtrahend.imaginary
    )

    override fun multiply(factor: BigGaussian): BigGaussian {
        val newReal = real * factor.real - imaginary * factor.imaginary
        val newImaginary = real * factor.imaginary + imaginary * factor.real
        return BigGaussian(newReal, newImaginary)
    }

    override fun divide(divisor: BigGaussian): BigComplex {
        require(divisor.isUnit) { "expected divisor to be a unit but divisor = $divisor" }
        val d = (divisor.real.pow(2) + divisor.imaginary.pow(2)).toBigDecimal()
        val r = (real * divisor.real + imaginary * divisor.imaginary).toBigDecimal() / d
        val i = (imaginary * divisor.real - real * divisor.imaginary).toBigDecimal() / d
        return BigComplex(r, i)
    }

    /**
     * Returns the quotient of this and the [divisor] based on the [context]
     *
     * @throws IllegalArgumentException if [divisor] is not a unit
     * @since 0.0.1
     */
    public fun divide(divisor: BigGaussian, context: FinnmathContext): BigComplex {
        require(divisor.isUnit) { "expected divisor to be a unit but divisor = $divisor" }
        val d = (divisor.real.pow(2) + divisor.imaginary.pow(2)).toBigDecimal(context.scale, context.mathContext)
        val r =
            (real * divisor.real + imaginary * divisor.imaginary).toBigDecimal(context.scale, context.mathContext) / d
        val i =
            (imaginary * divisor.real - real * divisor.imaginary).toBigDecimal(context.scale, context.mathContext) / d
        return BigComplex(r, i)
    }

    override fun pow(exponent: Int): BigComplex = when {
        exponent < 0 -> toBigComplex().multiply(pow(-exponent - 1)).invert()
        exponent == 0 -> BigComplex.ONE
        else -> toBigComplex().multiply(pow(exponent - 1))
    }

    /**
     * Returns the power of this raised by the [exponent] based on the [context]
     *
     * @since 0.0.1
     */
    public fun pow(exponent: Int, context: FinnmathContext): BigComplex = when {
        exponent < 0 -> toBigComplex(context).multiply(pow(-exponent - 1, context)).invert(context.mathContext)
        exponent == 0 -> BigComplex.ONE
        else -> toBigComplex(context).multiply(pow(exponent - 1, context))
    }

    override fun negate(): BigGaussian = BigGaussian(-real, -imaginary)

    override fun invert(): BigComplex {
        check(isUnit) { "expected this to be a unit but this = $this" }
        return ONE.divide(this)
    }

    /**
     * Returns the inverted BigGaussian based on the [context]
     *
     *@throws IllegalStateException if this is not a unit
     * @since 0.0.1
     */
    public fun invert(context: FinnmathContext): BigComplex {
        check(isUnit) { "expected this to be a unit but this = $this" }
        return ONE.divide(this, context)
    }

    override fun absPow2(): BigInteger = real.pow(2) + imaginary.pow(2)

    override fun abs(): BigDecimal = absPow2().sqrt()

    /**
     * Returns the absolute value based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun abs(mathContext: MathContext): BigDecimal = absPow2().sqrt(mathContext)

    /**
     * Returns the absolute value based on the [context]
     *
     * @since 0.0.1
     */
    public fun abs(context: FinnmathContext): BigDecimal = absPow2().sqrt(context)

    override fun conjugate(): BigGaussian = BigGaussian(real, -imaginary)

    override fun argument(): BigDecimal {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        val value = real.toBigDecimal().divide(abs())
        val acos = value.acos()
        return if (imaginary < BigInteger.ZERO) -acos else acos
    }

    /**
     * Returns the argument based on the [context]
     *
     * @throws IllegalStateException if this is equal to 0 by comparing
     * @since 0.0.1
     */
    public fun argument(context: FinnmathContext): BigDecimal {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        val value = real.toBigDecimal(context.scale, context.mathContext).divide(abs(context.mathContext))
        val acos = value.acos(context.mathContext)
        return if (imaginary < BigInteger.ZERO) -acos else acos
    }

    override fun toPolarForm(): BigPolarForm {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        return BigPolarForm(abs(), argument())
    }

    /**
     * Returns the [BigPolarForm] based on the [context]
     *
     * @throws IllegalStateException if this is equal to 0 by comparing
     * @since 0.0.1
     */
    public fun toPolarForm(context: FinnmathContext): BigPolarForm {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        return BigPolarForm(abs(context.mathContext), argument(context))
    }

    /**
     * Returns this as [BigComplex]
     *
     * @since 0.0.1
     */
    public fun toBigComplex(): BigComplex = BigComplex(real.toBigDecimal(), imaginary.toBigDecimal())

    /**
     * Returns this as [BigComplex] based on the [context]
     *
     * @since 0.0.1
     */
    public fun toBigComplex(context: FinnmathContext): BigComplex = BigComplex(
        real.toBigDecimal(context.scale, context.mathContext),
        imaginary.toBigDecimal(context.scale, context.mathContext)
    )

    override fun equalsByComparing(other: BigGaussian): Boolean =
        real.compareTo(other.real) == 0 && imaginary.compareTo(other.imaginary) == 0

    public companion object {
        /**
         * `0` as BigGaussian
         *
         * @since 0.0.1
         */
        @JvmField
        public val ZERO: BigGaussian = BigGaussian(BigInteger.ZERO)

        /**
         * `1` as BigGaussian
         *
         * @since 0.0.1
         */
        @JvmField
        public val ONE: BigGaussian = BigGaussian(BigInteger.ONE)

        /**
         * `i` as BigGaussian
         *
         * @since 0.0.1
         */
        @JvmField
        public val IMAGINARY: BigGaussian = BigGaussian(BigInteger.ZERO, BigInteger.ONE)

        /**
         * `-1` as BigGaussian
         *
         * @since 0.0.1
         */
        @JvmField
        public val MINUS_ONE: BigGaussian = ONE.negate()

        /**
         * `-i` as BigGaussian
         *
         * @since 0.0.1
         */
        @JvmField
        public val MINUS_IMAGINARY: BigGaussian = IMAGINARY.negate()

        /**
         * Units
         *
         * @since 0.0.1
         */
        @JvmField
        public val UNITS: Set<BigGaussian> = setOf(ONE, IMAGINARY, MINUS_ONE, MINUS_IMAGINARY)
    }
}
