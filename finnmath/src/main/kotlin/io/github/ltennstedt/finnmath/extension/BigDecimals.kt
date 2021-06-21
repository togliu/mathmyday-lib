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

import ch.obermuhlner.math.big.BigDecimalMath
import java.math.BigDecimal
import java.math.MathContext

/**
 * Returns the square root of [this][BigDecimal]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigDecimal.sqrt(mathContext: MathContext = MathContext.UNLIMITED): BigDecimal = BigDecimalMath.sqrt(
    this,
    mathContext
)

/**
 * Returns the sinus of [this][BigDecimal]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigDecimal.sin(mathContext: MathContext = MathContext.UNLIMITED): BigDecimal = BigDecimalMath.sin(
    this,
    mathContext
)

/**
 * Returns the cosinus of [this][BigDecimal]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigDecimal.cos(mathContext: MathContext = MathContext.UNLIMITED): BigDecimal = BigDecimalMath.cos(
    this,
    mathContext
)

/**
 * Returns the arc cosinus of [this][BigDecimal]
 * Default argument for [mathContext] is [MathContext.UNLIMITED]
 *
 * @since 0.0.1
 *
 */
public fun BigDecimal.acos(mathContext: MathContext = MathContext.UNLIMITED): BigDecimal = BigDecimalMath.acos(
    this,
    mathContext
)
