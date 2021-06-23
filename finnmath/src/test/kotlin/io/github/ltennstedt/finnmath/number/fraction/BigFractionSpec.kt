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

import io.github.ltennstedt.finnmath.extension.toBigFraction
import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.math.BigInteger

class BigFractionSpec : FunSpec({
    test("primary constructor should throw Exception when denominator is 0") {
        shouldThrowExactly<IllegalArgumentException> {
            BigFraction(BigInteger.ONE, BigInteger.ZERO)
        }
    }
    context("isInvertible") {
        test("should be false when numerator is 0") {
            BigFraction.ZERO.isInvertible shouldBe false
        }
        test("should be true when numerator is not 0") {
            BigFraction.ONE.isInvertible shouldBe true
        }
    }
    context("isUnit") {
        test("should be false when numerator is not 1") {
            BigInteger.TEN.toBigFraction().isUnit shouldBe false
        }
        test("should be true when numerator is 1") {
            BigFraction.ONE.isUnit shouldBe true
        }
    }
    context("isDyadic") {
        test("should be false when denominator is not a power of 2") {
            BigFraction(BigInteger.ONE, 3.toBigInteger()).isDyadic shouldBe false
        }
        test("should be true when denominator is a power of 2") {
            BigFraction(BigInteger.ONE, 4.toBigInteger()).isDyadic shouldBe true
        }
    }
})
