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

import ch.obermuhlner.math.big.BigDecimalMath // ktlint-disable import-ordering
import com.google.common.annotations.Beta
import io.github.ltennstedt.finnmath.FinnmathContext
import io.github.ltennstedt.finnmath.extension.sqrt
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import org.apiguardian.api.API

/**
 * Immutable implementation of a complex number which uses [BigDecimal] as type for its real and imaginary part
 *
 * @property real real part
 * @property imaginary imaginary part
 * @constructor Constructs a BigComplex
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public class BigComplex @JvmOverloads constructor(
    real: BigDecimal,
    imaginary: BigDecimal = BigDecimal.ZERO
) : AbstractComplex<BigDecimal, BigComplex, BigComplex, BigDecimal, BigPolarForm>(
    real,
    imaginary
) {
    override val isUnit: Boolean by lazy { doesNotEqualByComparing(ZERO) }

    /**
     * Constructs a BigComplex from [real] and [imaginary]
     *
     * Default argument for [imaginary] is `0`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Int, imaginary: Int = 0) : this(real.toBigDecimal(), imaginary.toBigDecimal())

    /**
     * Constructs a BigComplex from [real] and [imaginary] based on the [mathContext]
     *
     * Default argument for [imaginary] is `0`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Int, imaginary: Int = 0, mathContext: MathContext) : this(
        real.toBigDecimal(mathContext),
        imaginary.toBigDecimal(mathContext)
    )

    /**
     * Constructs a BigComplex from [real] and [imaginary]
     *
     * Default argument for [imaginary] is `0`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Long, imaginary: Long = 0L) : this(real.toBigDecimal(), imaginary.toBigDecimal())

    /**
     * Constructs a BigComplex from [real] and [imaginary] based on the [mathContext]
     *
     * Default argument for [imaginary] is `0`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Long, imaginary: Long = 0, mathContext: MathContext) : this(
        real.toBigDecimal(mathContext),
        imaginary.toBigDecimal(mathContext)
    )

    /**
     * Constructs a BigComplex from [real] and [imaginary]
     *
     * Default argument for [imaginary] is `0.0F`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Float, imaginary: Float = 0.0F) : this(real.toBigDecimal(), imaginary.toBigDecimal())

    /**
     * Constructs a BigComplex from [real] and [imaginary] based on the [mathContext]
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Float, imaginary: Float = 0.0F, mathContext: MathContext) : this(
        real.toBigDecimal(mathContext),
        imaginary.toBigDecimal(mathContext)
    )

    /**
     * Constructs a BigComplex from [real] and [imaginary]
     *
     * Default argument for [imaginary] is `0.0`
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Double, imaginary: Double = 0.0) : this(real.toBigDecimal(), imaginary.toBigDecimal())

    /**
     * Constructs a BigComplex from [real] and [imaginary] based on the [mathContext]
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: Double, imaginary: Double = 0.0, mathContext: MathContext) : this(
        real.toBigDecimal(mathContext),
        imaginary.toBigDecimal(mathContext)
    )

    /**
     * Constructs a BigComplex from [real] and [imaginary]
     *
     * Default argument for [imaginary] is [BigInteger.ZERO]
     *
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(real: BigInteger, imaginary: BigInteger = BigInteger.ZERO) : this(
        real.toBigDecimal(),
        imaginary.toBigDecimal()
    )

    /**
     * Constructs a BigComplex from [real] and [imaginary] based on the [context]
     *
     * Default argument for [imaginary] is [BigInteger.ZERO]
     *
     * @since 0.0.1
     */
    public constructor(
        real: BigInteger,
        imaginary: BigInteger = BigInteger.ZERO,
        context: FinnmathContext
    ) : this(
        real.toBigDecimal(context.scale, context.mathContext),
        imaginary.toBigDecimal(context.scale, context.mathContext)
    )

    override fun add(summand: BigComplex): BigComplex = BigComplex(real + summand.real, imaginary + summand.imaginary)

    /**
     * Returns the sum of this and the [summand] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun add(summand: BigComplex, mathContext: MathContext): BigComplex = BigComplex(
        real.add(summand.real, mathContext),
        imaginary.add(summand.imaginary, mathContext)
    )

    override fun subtract(subtrahend: BigComplex): BigComplex = BigComplex(
        real - subtrahend.real,
        imaginary - subtrahend.imaginary
    )

    /**
     * Returns the difference of this and the [subtrahend] based on the [mathContext]
     *
     * Default argument for [mathContext] is [MathContext.UNLIMITED]
     *
     * @since 0.0.1
     */
    public fun subtract(subtrahend: BigComplex, mathContext: MathContext = MathContext.UNLIMITED): BigComplex =
        BigComplex(
            real.subtract(subtrahend.real, mathContext),
            imaginary.subtract(subtrahend.imaginary, mathContext)
        )

    override fun multiply(factor: BigComplex): BigComplex = BigComplex(
        real * factor.real - imaginary * factor.imaginary,
        real * factor.imaginary + imaginary * factor.real
    )

    /**
     * Returns the product of this and the [factor] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun multiply(factor: BigComplex, mathContext: MathContext): BigComplex {
        val re = real.multiply(factor.real, mathContext)
            .subtract(imaginary.multiply(factor.imaginary, mathContext), mathContext)
        val im =
            real.multiply(factor.imaginary, mathContext).add(imaginary.multiply(factor.real, mathContext), mathContext)
        return BigComplex(re, im)
    }

    override fun divide(divisor: BigComplex): BigComplex {
        require(divisor.isUnit) { "divisor expected to be a unit but divisor = $divisor" }
        val den = divisor.real.pow(2) + divisor.imaginary.pow(2)
        return BigComplex(
            (real * divisor.real + imaginary * divisor.imaginary) / den,
            (imaginary * divisor.real - real * divisor.imaginary) / den
        )
    }

    /**
     * Returns the quotient of this and the [divisor] based on the [mathContext]
     *
     * @throws IllegalArgumentException if [divisor] is not a unit
     * @since 0.0.1
     */
    public fun divide(divisor: BigComplex, mathContext: MathContext): BigComplex {
        require(divisor.isUnit) { "divisor expected to be a unit but divisor = $divisor" }
        val den = divisor.real.pow(2, mathContext).add(divisor.imaginary.pow(2, mathContext), mathContext)
        val re = real.multiply(divisor.real, mathContext)
            .add(imaginary.multiply(divisor.imaginary, mathContext), mathContext)
            .divide(den, mathContext)
        val im = imaginary.multiply(divisor.real, mathContext)
            .subtract(real.multiply(divisor.imaginary, mathContext), mathContext)
            .divide(den, mathContext)
        return BigComplex(re, im)
    }

    override fun pow(exponent: Int): BigComplex = when {
        exponent < 0 -> (this * pow(-exponent - 1)).invert()
        exponent == 0 -> ONE
        else -> this * pow(exponent - 1)
    }

    /**
     * Returns the power of this raised by the [exponent] based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun pow(exponent: Int, mathContext: MathContext): BigComplex = when {
        exponent < 0 -> multiply(pow(-exponent - 1, mathContext), mathContext).invert(mathContext)
        exponent == 0 -> ONE
        else -> multiply(pow(exponent - 1, mathContext), mathContext)
    }

    override fun negate(): BigComplex = BigComplex(-real, -imaginary)

    /**
     * Returns the negated BigComplex based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun negate(mathContext: MathContext): BigComplex =
        BigComplex(real.negate(mathContext), imaginary.negate(mathContext))

    override fun invert(): BigComplex {
        check(isUnit) { "expected this to be a unit but this = $this" }
        return ONE / this
    }

    /**
     * Returns the inverted BigComplex based on the [mathContext]
     *
     *@throws IllegalStateException if this is not a unit
     * @since 0.0.1
     */
    public fun invert(mathContext: MathContext): BigComplex {
        check(isUnit) { "expected this to be a unit but this = $this" }
        return ONE.divide(this, mathContext)
    }

    override fun absPow2(): BigDecimal = real.pow(2) + imaginary.pow(2)

    /**
     * Returns the square of the absolute value based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun absPow2(mathContext: MathContext): BigDecimal =
        real.pow(2, mathContext).add(imaginary.pow(2, mathContext), mathContext)

    override fun abs(): BigDecimal = absPow2().sqrt()

    /**
     * Returns the absolute value based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun abs(mathContext: MathContext): BigDecimal = absPow2(mathContext).sqrt(mathContext)

    override fun conjugate(): BigComplex = BigComplex(real, -imaginary)

    /**
     * Returns the conjugated BigComplex based on the [mathContext]
     *
     * @since 0.0.1
     */
    public fun conjugate(mathContext: MathContext): BigComplex = BigComplex(real, imaginary.negate(mathContext))

    override fun argument(): BigDecimal {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        val value = real / abs()
        val acos = BigDecimalMath.acos(value, MathContext.UNLIMITED)
        return if (imaginary < BigDecimal.ZERO) -acos else acos
    }

    /**
     * Returns the argument based on the [mathContext]
     *
     * @throws IllegalStateException if this is equal to 0 by comparing
     * @since 0.0.1
     */
    public fun argument(mathContext: MathContext): BigDecimal {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        val value = real.divide(abs(mathContext), mathContext)
        val acos = BigDecimalMath.acos(value, mathContext)
        return if (imaginary < BigDecimal.ZERO) acos.negate(mathContext) else acos
    }

    override fun toPolarForm(): BigPolarForm {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        return BigPolarForm(abs(), argument())
    }

    /**
     * Returns this as [BigPolarForm]
     *
     * @throws IllegalStateException if this is equal to 0 by comparing
     * @since 0.0.1
     */
    public fun toPolarForm(mathContext: MathContext): BigPolarForm {
        check(doesNotEqualByComparing(ZERO)) { "expected this != 0 but this = $this" }
        return BigPolarForm(abs(mathContext), argument(mathContext))
    }

    override fun equalsByComparing(other: BigComplex): Boolean =
        real.compareTo(other.real) == 0 && imaginary.compareTo(other.imaginary) == 0

    public companion object {
        /**
         * `0` as BigComplex
         *
         * @since 0.0.1
         */
        @JvmField
        public val ZERO: BigComplex = BigComplex(BigDecimal.ZERO)

        /**
         * `1` as BigComplex
         *
         * @since 0.0.1
         */
        @JvmField
        public val ONE: BigComplex = BigComplex(BigDecimal.ONE)

        /**
         * `i` as BigComplex
         *
         * @since 0.0.1
         */
        @JvmField
        public val IMAGINARY: BigComplex = BigComplex(BigDecimal.ZERO, BigDecimal.ONE)

        /**
         * `-1` as BigComplex
         *
         * @since 0.0.1
         */
        @JvmField
        public val MINUS_ONE: BigComplex = ONE.negate()

        /**
         * `-i` as BigComplex
         *
         * @since 0.0.1
         */
        @JvmField
        public val MINUS_IMAGINARY: BigComplex = IMAGINARY.negate()

        /**
         * Units
         *
         * @since 0.0.1
         */
        @JvmField
        public val UNITS: Set<BigComplex> = setOf(ONE, IMAGINARY, MINUS_ONE, MINUS_IMAGINARY)
    }
}
