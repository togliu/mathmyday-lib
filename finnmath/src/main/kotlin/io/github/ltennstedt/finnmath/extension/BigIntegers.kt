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

package io.github.ltennstedt.finnmath.extension

import com.google.common.math.BigIntegerMath
import io.github.ltennstedt.finnmath.FinnmathContext
import io.github.ltennstedt.finnmath.number.complex.BigComplex
import io.github.ltennstedt.finnmath.number.complex.BigGaussian
import io.github.ltennstedt.finnmath.number.fraction.BigFraction
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext

/**
 * Returns if [this][BigInteger] is a power of 2
 *
 * @since 0.0.1
 */
public fun BigInteger.isPowerOfTwo(): Boolean = BigIntegerMath.isPowerOfTwo(this)

/**
 * Returns the square root of [this][BigInteger] based on the [mathContext]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.sqrt(mathContext: MathContext = MathContext.UNLIMITED): BigDecimal =
    toBigDecimal().sqrt(mathContext)

/**
 * Returns the square root of [this][BigInteger] based on the [context]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.sqrt(context: FinnmathContext): BigDecimal =
    toBigDecimal(context.scale, context.mathContext).sqrt(context.mathContext)

/**
 * Returns the sinus of [this][BigInteger] based on the [mathContext]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.sin(mathContext: MathContext = MathContext.UNLIMITED): BigDecimal =
    toBigDecimal().sin(mathContext)

/**
 * Returns the sinus of [this][BigInteger] based on the [context]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.sin(context: FinnmathContext): BigDecimal =
    toBigDecimal(context.scale, context.mathContext).sin(context.mathContext)

/**
 * Returns the cosinus of [this][BigInteger] based on the [mathContext]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.cos(mathContext: MathContext = MathContext.UNLIMITED): BigDecimal =
    toBigDecimal().cos(mathContext)

/**
 * Returns the cosinus based on the [context]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.cos(context: FinnmathContext): BigDecimal =
    toBigDecimal(context.scale, context.mathContext).cos(context.mathContext)

/**
 * Returns the arc cosinus based on the [mathContext]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.acos(mathContext: MathContext = MathContext.UNLIMITED): BigDecimal =
    toBigDecimal().acos(mathContext)

/**
 * Returns the arc cosinus based on the [context]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.acos(context: FinnmathContext): BigDecimal =
    toBigDecimal(context.scale, context.mathContext).acos(context.mathContext)

/**
 * Returns this as [BigFraction]
 *
 * @since 0.0.1
 */
public fun BigInteger.toBigFraction(): BigFraction = BigFraction(this, BigInteger.ONE)

/**
 * Returns this as [BigGaussian]
 *
 * @since 0.0.1
 */
public fun BigInteger.toBigGaussian(): BigGaussian = BigGaussian(this)

/**
 * Returns this as [BigComplex]
 *
 * @since 0.0.1
 */
public fun BigInteger.toBigComplex(): BigComplex = BigComplex(this)

/**
 * Returns this as [BigComplex] based on the [context]
 *
 * @since 0.0.1
 */
public fun BigInteger.toBigComplex(context: FinnmathContext): BigComplex =
    BigComplex(this, BigInteger.ZERO, context)
