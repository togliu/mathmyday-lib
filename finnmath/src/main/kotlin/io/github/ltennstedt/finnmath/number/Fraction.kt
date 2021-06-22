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

import com.google.common.math.LongMath
import io.github.ltennstedt.finnmath.extension.gcd
import io.github.ltennstedt.finnmath.extension.isPowerOfTwo
import java.math.BigDecimal
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * Immutable implementation of a fraction which uses [Long] as type for its [numerator] and [denominator]
 *
 * The returned Fractions of most methods are neither normalized nor reduced
 *
 * @property numerator numerator
 * @property denominator denominator; default argument is 1
 * @constructor Constructs a Fraction
 * @throws IllegalArgumentException `if [denominator] == 0`
 * @see .normalize
 * @see .reduce
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class Fraction @JvmOverloads constructor(
    numerator: Long,
    denominator: Long = 1L
) : AbstractFraction<Long, Fraction, FractionRange>(
    numerator,
    denominator
) {
    init {
        require(denominator != 0L) { "expected denominator != 0 but denominator = $denominator" }
    }

    override val isInvertible: Boolean
        get() = numerator != 0L

    override val isUnit: Boolean
        get() = numerator == 1L

    override val isDyadic: Boolean by lazy { denominator.isPowerOfTwo() }

    override val isIrreducible: Boolean by lazy { numerator.gcd(denominator) == 1L }

    override val isProper: Boolean by lazy { abs().lessThan(ONE) }

    override val signum: Int
        get() = numerator.sign * denominator.sign

    /**
     * Constructs a Fraction from the [numerator] and [denominator]
     *
     * Default argument for [denominator] is `1`.
     *
     * @throws IllegalArgumentException if `denominator == 0`
     * @since 0.0.1
     */
    @JvmOverloads
    public constructor(numerator: Int, denominator: Int = 1) : this(
        numerator.toLong(),
        denominator.toLong()
    )

    override fun add(summand: Fraction): Fraction {
        val newNumerator = summand.denominator * numerator + denominator * summand.numerator
        val newDenominator = denominator * summand.denominator
        return Fraction(newNumerator, newDenominator)
    }

    override fun subtract(subtrahend: Fraction): Fraction {
        val newNumerator = subtrahend.denominator * numerator - denominator * subtrahend.numerator
        val newDenominator = denominator * subtrahend.denominator
        return Fraction(newNumerator, newDenominator)
    }

    override fun multiply(factor: Fraction): Fraction =
        Fraction(numerator * factor.numerator, denominator * factor.denominator)

    override fun divide(divisor: Fraction): Fraction {
        require(divisor.isInvertible) { "expected divisor to be invertible but divisor = $divisor" }
        return multiply(divisor.invert())
    }

    override fun pow(exponent: Int): Fraction = when {
        exponent > 0 -> multiply(pow(exponent - 1))
        exponent < 0 -> multiply(pow(-exponent - 1)).invert()
        else -> ONE
    }

    override fun negate(): Fraction = Fraction(-numerator, denominator)

    override fun invert(): Fraction {
        check(isInvertible) { "expected this to be invertible but this = $this" }
        return Fraction(denominator, numerator)
    }

    override fun abs(): Fraction = Fraction(numerator.absoluteValue, denominator.absoluteValue)

    override fun lessThanOrEqualTo(other: Fraction): Boolean {
        val normalized = normalize()
        val normalizedOther = other.normalize()
        val left = normalizedOther.denominator * normalized.numerator
        val right = normalized.denominator * normalizedOther.numerator
        return left <= right
    }

    override fun inc(): Fraction = add(ONE)

    override fun dec(): Fraction = subtract(ONE)

    override fun normalize(): Fraction = when {
        signum < 0 && numerator.sign > 0 -> Fraction(-numerator, denominator.absoluteValue)
        signum < 0 -> this
        signum == 0 -> ZERO
        else -> abs()
    }

    override fun reduce(): Fraction {
        val gcd = LongMath.gcd(numerator, denominator)
        return Fraction(numerator / gcd, denominator / gcd)
    }

    override fun expand(number: Long): Fraction = Fraction(number * numerator, number * denominator)

    /**
     * Returns this..[other]
     *
     * @since 0.0.1
     */
    public operator fun rangeTo(other: Fraction): FractionRange = FractionRange(this, other)

    override fun compareTo(other: Fraction): Int = COMPARATOR.compare(this, other)

    override fun toBigDecimal(): BigDecimal = numerator.toBigDecimal().divide(denominator.toBigDecimal())

    public companion object {
        /**
         * `0` as Fraction
         *
         * @since 0.0.1
         */
        @JvmField
        public val ZERO: Fraction = Fraction(0L)

        /**
         * `1` as Fraction
         *
         * @since 0.0.1
         */
        @JvmField
        public val ONE: Fraction = Fraction(1L)

        /**
         * Units
         *
         * @since 0.0.1
         */
        @JvmField
        public val UNITS: Sequence<Fraction> = generateSequence(ONE) { Fraction(1L, it.denominator.inc()) }

        /**
         * [Comparator]
         *
         * @since 0.0.1
         */
        @JvmField
        public val COMPARATOR: Comparator<Fraction> = Comparator { a: Fraction, b: Fraction ->
            when {
                a.lessThan(b) -> -1
                a.greaterThan(b) -> 1
                else -> 0
            }
        }
    }
}
