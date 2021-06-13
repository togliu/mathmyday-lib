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
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * Immutable implementation of a fraction which uses [Long] as type for its [numerator] and [denominator]
 *
 * The returned [Fractions][LongFraction] of most methods are neither normalized nor reduced
 *
 * @property numerator numerator
 * @property denominator denominator; default argument is [BigInteger.ONE]
 * @constructor Constructs a [LongFraction]
 * @throws IllegalArgumentException `if [denominator] == 0`
 * @see .normalize
 * @see .reduce
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@Suppress("TooManyFunctions")
public class LongFraction @JvmOverloads constructor(
    numerator: Long,
    denominator: Long = 1L
) : AbstractFraction<Long, LongFraction, LongFractionRange>(
    numerator,
    denominator
) {
    init {
        require(denominator != 0L) { "expected denominator != 0 but denominator = $denominator" }
    }

    override val isInvertible: Boolean get() = numerator != 0L

    override val isUnit: Boolean get() = numerator == 1L

    override val isDyadic: Boolean by lazy { LongMath.isPowerOfTwo(denominator) }

    override val isIrreducible: Boolean by lazy { LongMath.gcd(numerator, denominator) == 1L }

    override val isProper: Boolean by lazy { abs().lessThan(ONE) }

    override val signum: Int get() = numerator.sign * denominator.sign

    /**
     * Constructs a [LongFraction] from the [numerator] and [denominator]
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

    override fun add(summand: LongFraction): LongFraction {
        val newNumerator = summand.denominator * numerator + denominator * summand.numerator
        val newDenominator = denominator * summand.denominator
        return LongFraction(newNumerator, newDenominator)
    }

    override fun subtract(subtrahend: LongFraction): LongFraction {
        val newNumerator = subtrahend.denominator * numerator - denominator * subtrahend.numerator
        val newDenominator = denominator * subtrahend.denominator
        return LongFraction(newNumerator, newDenominator)
    }

    override fun multiply(factor: LongFraction): LongFraction =
        LongFraction(numerator * factor.numerator, denominator * factor.denominator)

    override fun divide(divisor: LongFraction): LongFraction {
        require(divisor.isInvertible) { "expected divisor to be invertible but divisor = $divisor" }
        return multiply(divisor.invert())
    }

    override fun pow(exponent: Int): LongFraction = when {
        exponent > 0 -> multiply(pow(exponent - 1))
        exponent < 0 -> multiply(pow(-exponent - 1)).invert()
        else -> ONE
    }

    override fun negate(): LongFraction = LongFraction(-numerator, denominator)

    override fun invert(): LongFraction {
        check(isInvertible) { "expected this to be invertible but this = $this" }
        return LongFraction(denominator, numerator)
    }

    override fun abs(): LongFraction = LongFraction(numerator.absoluteValue, denominator.absoluteValue)

    override fun lessThanOrEqualTo(other: LongFraction): Boolean {
        val normalized = normalize()
        val normalizedOther = other.normalize()
        val left = normalizedOther.denominator * normalized.numerator
        val right = normalized.denominator * normalizedOther.numerator
        return left <= right
    }

    override fun inc(): LongFraction = add(ONE)

    override fun dec(): LongFraction = subtract(ONE)

    override fun normalize(): LongFraction = when {
        signum < 0 && numerator.sign > 0 -> LongFraction(-numerator, denominator.absoluteValue)
        signum < 0 -> this
        signum == 0 -> ZERO
        else -> abs()
    }

    override fun reduce(): LongFraction {
        val gcd = LongMath.gcd(numerator, denominator)
        return LongFraction(numerator / gcd, denominator / gcd)
    }

    override fun expand(number: Long): LongFraction = LongFraction(number * numerator, number * denominator)

    /**
     * Returns [this][LongFraction]..[other]
     *
     * @since 0.0.1
     */
    public operator fun rangeTo(other: LongFraction): LongFractionRange = LongFractionRange(this, other)

    override fun compareTo(other: LongFraction): Int = COMPARATOR.compare(this, other)

    override fun toByte(): Byte = toBigDecimal().toByte()

    override fun toShort(): Short = toBigDecimal().toShort()

    override fun toInt(): Int = toBigDecimal().toInt()

    override fun toLong(): Long = toBigDecimal().toLong()

    override fun toFloat(): Float = toBigDecimal().toFloat()

    override fun toDouble(): Double = toBigDecimal().toDouble()

    override fun toBigDecimal(): BigDecimal = numerator.toBigDecimal().divide(denominator.toBigDecimal())

    override fun toChar(): Char = toBigDecimal().toChar()

    public companion object {
        /**
         * `0` as [LongFraction]
         *
         * @since 0.0.1
         */
        @JvmField
        public val ZERO: LongFraction = LongFraction(0L, 1L)

        /**
         * `1` as [LongFraction]
         *
         * @since 0.0.1
         */
        @JvmField
        public val ONE: LongFraction = LongFraction(1L, 1L)

        /**
         * Units
         *
         * @since 0.0.1
         */
        @JvmField
        public val UNITS: Sequence<LongFraction> =
            generateSequence(ONE) { LongFraction(it.denominator.inc()).invert() }

        /**
         * [Comparator]
         *
         * @since 0.0.1
         */
        @JvmField
        public val COMPARATOR: Comparator<LongFraction> = Comparator { a: LongFraction, b: LongFraction ->
            when {
                a.lessThan(b) -> -1
                a.greaterThan(b) -> 1
                else -> 0
            }
        }
    }
}
