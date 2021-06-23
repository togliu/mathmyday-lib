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

package io.github.ltennstedt.finnmath.linear.field

import java.math.BigDecimal
import java.math.BigInteger

/**
 * Single implementation of the [BigInteger] field
 *
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public object BigIntegerField : Field<BigInteger, BigDecimal> {
    override val addition: (a: BigInteger, b: BigInteger) -> BigInteger
        get() = BigInteger::add

    override val subtraction: (a: BigInteger, b: BigInteger) -> BigInteger
        get() = BigInteger::subtract

    override val multiplication: (a: BigInteger, b: BigInteger) -> BigInteger
        get() = BigInteger::multiply

    override val division: (a: BigInteger, b: BigInteger) -> BigDecimal
        get() = { a, b -> a.toBigDecimal() / b.toBigDecimal() }

    override val equalityByComparing: (a: BigInteger, b: BigInteger) -> Boolean
        get() = { a, b -> a.compareTo(b) == 0 }

    override val zero: BigInteger
        get() = BigInteger.ZERO
}
