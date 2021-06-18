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

import com.google.common.math.LongMath
import io.github.ltennstedt.finnmath.arbLong
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.PropertyTesting
import io.kotest.property.forAll
import kotlin.math.absoluteValue

@Suppress("UNUSED")
object LongsSpec : FunSpec({
    PropertyTesting.defaultIterationCount = 10
    context("isEven") {
        test("should return false when a Long is odd") {
            1L.isEven() shouldBe false
        }
        test("should return true when a Long is even") {
            2L.isEven() shouldBe true
        }
    }
    context("isOdd") {
        test("should return false when a Long is even") {
            2L.isOdd() shouldBe false
        }
        test("should return true when a Long is odd") {
            1L.isOdd() shouldBe true
        }
    }
    context("gcd") {
        test("should call LongMath.gcd with absolute values") {
            forAll(arbLong, arbLong) { a, b ->
                a.gcd(b) == LongMath.gcd(a.absoluteValue, b.absoluteValue)
            }
        }
    }
})
