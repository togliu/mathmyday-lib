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

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.math.BigInteger

@Suppress("UNUSED")
object BigIntegersSpec : FunSpec({
    context("isPowerOfTwo") {
        test("should be false when a BigInteger is not a power of 2") {
            BigInteger.valueOf(3L).isPowerOfTwo() shouldBe false
        }
        test("should be true when a BigInteger is a power of 2") {
            BigInteger.valueOf(4L).isPowerOfTwo() shouldBe true
        }
    }
    test("toBigFraction should return a BigInteger as BigFraction") {
        val actual = BigInteger.TEN.toBigFraction()
        actual.numerator shouldBeSameInstanceAs BigInteger.TEN
        actual.denominator shouldBeSameInstanceAs BigInteger.ONE
    }
})
