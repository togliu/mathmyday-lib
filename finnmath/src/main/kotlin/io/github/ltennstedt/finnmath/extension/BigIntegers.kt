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
import io.github.ltennstedt.finnmath.number.BigFraction
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
 * Returns the square root of [this][BigInteger] based on the [scale] and [mathContext]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.sqrt(scale: Int, mathContext: MathContext): BigDecimal =
    toBigDecimal(scale, mathContext).sqrt(mathContext)

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
 * Returns the sinus of [this][BigInteger] based on the [scale] and [mathContext]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.sin(scale: Int, mathContext: MathContext): BigDecimal =
    toBigDecimal(scale, mathContext).sin(mathContext)

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
 * Returns the cosinus of [this][BigInteger] based on the [scale] and [mathContext]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.cos(scale: Int, mathContext: MathContext): BigDecimal =
    toBigDecimal(scale, mathContext).cos(mathContext)

/**
 * Returns the arc cosinus of [this][BigInteger] based on the [mathContext]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.acos(mathContext: MathContext = MathContext.UNLIMITED): BigDecimal =
    toBigDecimal().acos(mathContext)

/**
 * Returns the arc cosinus of [this][BigInteger] based on the [scale] and [mathContext]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigInteger.acos(scale: Int, mathContext: MathContext): BigDecimal =
    toBigDecimal(scale, mathContext).acos(mathContext)

/**
 * Returns [this][BigInteger] as [BigFraction]
 *
 * @since 0.0.1
 */
public fun BigInteger.toBigFraction(): BigFraction = BigFraction(this, BigInteger.ONE)
