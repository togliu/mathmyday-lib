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

package io.github.ltennstedt.finnmath.extension

import com.google.common.math.IntMath
import io.github.ltennstedt.finnmath.number.BigFraction
import io.github.ltennstedt.finnmath.number.Fraction
import kotlin.math.absoluteValue

/**
 * Returns if [this][Int] is even
 *
 * @since 0.0.1
 */
public fun Int.isEven(): Boolean = this % 2 == 0

/**
 * Returns if [this][Int] is odd
 *
 * @since 0.0.1
 */
public fun Int.isOdd(): Boolean = this % 2 != 0

/**
 * Returns if [this][Int] is a power of 2
 *
 * @since 0.0.1
 */
public fun Int.isPowerOfTwo(): Boolean = IntMath.isPowerOfTwo(this)

/**
 * Returns the positive GCD of [this][Int] and [other]
 *
 * @since 0.0.1
 */
public fun Int.gcd(other: Int): Int = IntMath.gcd(this.absoluteValue, other.absoluteValue)

/**
 * Returns [this][Int] as [Fraction]
 *
 * @since 0.0.1
 */
public fun Int.toFraction(): Fraction = Fraction(this)

/**
 * Returns [this][Int] as [BigFraction]
 *
 * @since 0.0.1
 */
public fun Int.toBigFraction(): BigFraction = BigFraction(this)
