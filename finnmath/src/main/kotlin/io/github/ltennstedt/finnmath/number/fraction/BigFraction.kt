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

package io.github.ltennstedt.finnmath.number.fraction

import io.github.ltennstedt.finnmath.extension.isPowerOfTwo
import io.github.ltennstedt.finnmath.number.range.BigFractionRange
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Immutable implementation of a fraction which uses [BigInteger] as type for its [numerator] and [denominator]
 *
 * The returned BigFractions of most methods are neither normalized nor reduced
 *
 * @property numerator numerator
 * @property denominator denominator; default argument is [BigInteger.ONE]
 * @constructor Constructs a BigFraction
 * @throws IllegalArgumentException `if [denominator] == 0`
 * @see .normalize
 * @see .reduce
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class BigFraction @JvmOverloads constructor(
    numerator: BigInteger,
    denominator: BigInteger = BigInteger.ONE
) : AbstractFraction<BigInteger, BigFraction, BigFractionRange>(numerator, denominator) {
    init {
        require(denominator != BigInteger.ZERO) { "expected denominator != 0 but denominator = $denominator" }
    }

    override val isInvertible: Boolean
        get() = numerator != BigInteger.ZERO

    override val isUnit: Boolean
        get() = numerator == BigInteger.ONE

    override val isDyadic: Boolean by lazy { denominator.isPowerOfTwo() }

    override val isIrreducible: Boolean by lazy { numerator.gcd(denominator) == BigInteger.ONE }

    override val isProper: Boolean by lazy { abs().lessThan(ONE) }

    override val signum: Int
        get() = numerator.signum() * denominator.signum()

    /**
     * Constructs a BigFraction from the [numerator] and [denominator]
     *
     * Default argument for [denominator] is `1`.
     *
     * @throws IllegalArgumentException if `denominator == 0`
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(numerator: Int, denominator: Int = 1) : this(
        numerator.toBigInteger(),
        denominator.toBigInteger()
    )

    /**
     * Constructs a BigFraction from the [numerator] and [denominator]
     *
     * Default argument for [denominator] is `1`.
     *
     * @throws IllegalArgumentException if [denominator] == 0
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(numerator: Long, denominator: Long = 1L) : this(
        numerator.toBigInteger(),
        denominator.toBigInteger()
    )

    override fun add(summand: BigFraction): BigFraction {
        val newNumerator = summand.denominator * numerator + denominator * summand.numerator
        val newDenominator = denominator * summand.denominator
        return BigFraction(newNumerator, newDenominator)
    }

    override fun subtract(subtrahend: BigFraction): BigFraction {
        val newNumerator = subtrahend.denominator * numerator - denominator * subtrahend.numerator
        val newDenominator = denominator * subtrahend.denominator
        return BigFraction(newNumerator, newDenominator)
    }

    override fun multiply(factor: BigFraction): BigFraction =
        BigFraction(numerator * factor.numerator, denominator * factor.denominator)

    override fun divide(divisor: BigFraction): BigFraction {
        require(divisor.isInvertible) { "expected divisor to be invertible but divisor = $divisor" }
        return multiply(divisor.invert())
    }

    override fun pow(exponent: Int): BigFraction = when {
        exponent > 0 -> multiply(pow(exponent - 1))
        exponent < 0 -> multiply(pow(-exponent - 1)).invert()
        else -> ONE
    }

    override fun negate(): BigFraction = BigFraction(-numerator, denominator)

    override fun invert(): BigFraction {
        check(isInvertible) { "expected this to be invertible but this = $this" }
        return BigFraction(denominator, numerator)
    }

    override fun abs(): BigFraction = BigFraction(numerator.abs(), denominator.abs())

    override fun lessThanOrEqualTo(other: BigFraction): Boolean {
        val normalized = normalize()
        val normalizedOther = other.normalize()
        val left = normalizedOther.denominator * normalized.numerator
        val right = normalized.denominator * normalizedOther.numerator
        return left <= right
    }

    override fun inc(): BigFraction = add(ONE)

    override fun dec(): BigFraction = subtract(ONE)

    override fun normalize(): BigFraction = when {
        signum < 0 && numerator.signum() > 0 -> BigFraction(-numerator, denominator.abs())
        signum < 0 -> this
        signum == 0 -> ZERO
        else -> abs()
    }

    override fun reduce(): BigFraction {
        val gcd = numerator.gcd(denominator)
        return BigFraction(numerator / gcd, denominator / gcd)
    }

    override fun expand(number: BigInteger): BigFraction = BigFraction(number * numerator, number * denominator)

    /**
     * Returns this..[other]
     *
     * @since 0.0.1
     */
    public operator fun rangeTo(other: BigFraction): BigFractionRange = BigFractionRange(this, other)

    override fun compareTo(other: BigFraction): Int = COMPARATOR.compare(this, other)

    override fun toBigDecimal(): BigDecimal = numerator.toBigDecimal().divide(denominator.toBigDecimal())

    public companion object {
        /**
         * `0` as BigFraction
         *
         * @since 0.0.1
         */
        @JvmField
        public val ZERO: BigFraction = BigFraction(BigInteger.ZERO)

        /**
         * `1` as BigFraction
         *
         * @since 0.0.1
         */
        @JvmField
        public val ONE: BigFraction = BigFraction(BigInteger.ONE)

        /**
         * Units
         *
         * @since 0.0.1
         */
        @JvmField
        public val UNITS: Sequence<BigFraction> =
            generateSequence(ONE) { BigFraction(BigInteger.ONE, it.denominator.inc()) }

        /**
         * [Comparator]
         *
         * @since 0.0.1
         */
        @JvmField
        public val COMPARATOR: Comparator<BigFraction> = Comparator { a: BigFraction, b: BigFraction ->
            when {
                a.lessThan(b) -> -1
                a.greaterThan(b) -> 1
                else -> 0
            }
        }
    }
}
