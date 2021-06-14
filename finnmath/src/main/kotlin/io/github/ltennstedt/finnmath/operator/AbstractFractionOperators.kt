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

package io.github.ltennstedt.finnmath.operator

import io.github.ltennstedt.finnmath.number.AbstractFraction

public operator fun <N : Number, T : AbstractFraction<N, T, R>, R : ClosedRange<T>> AbstractFraction<N, T, R>.plus(
    summand: T
): T = add(summand)

public operator fun <N : Number, T : AbstractFraction<N, T, R>, R : ClosedRange<T>> AbstractFraction<N, T, R>.minus(
    subtrahend: T
): T = subtract(subtrahend)

public operator fun <N : Number, T : AbstractFraction<N, T, R>, R : ClosedRange<T>> AbstractFraction<N, T, R>.times(
    factor: T
): T = multiply(factor)

public operator fun <N : Number, T : AbstractFraction<N, T, R>, R : ClosedRange<T>> AbstractFraction<N, T, R>.div(
    divisor: T
): T = divide(divisor)

@Suppress("UNCHECKED_CAST")
public operator fun <
    N : Number,
    T : AbstractFraction<N, T, R>,
    R : ClosedRange<T>
    > AbstractFraction<N, T, R>.unaryPlus(): T = this as T

@Suppress("UNCHECKED_CAST")
public operator fun <
    N : Number,
    T : AbstractFraction<N, T, R>,
    R : ClosedRange<T>
    > AbstractFraction<N, T, R>.unaryMinus(): T = negate()
