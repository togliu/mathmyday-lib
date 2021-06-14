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
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.forAll
import kotlin.math.absoluteValue

@Suppress("UNUSED")
object IntExtensionsSpec : FunSpec({
    context("isEven") {
        test("should return false when an Int is odd") {
            val actual = 1.isEven()

            actual shouldBe false
        }
        test("should return true when an Int is even") {
            val actual = 2.isEven()

            actual shouldBe true
        }
    }
    context("isOdd") {
        test("should return false when an Int is even") {
            val actual = 2.isOdd()

            actual shouldBe false
        }
        test("should return true when an Int is odd") {
            val actual = 1.isOdd()

            actual shouldBe true
        }
    }
    context("gcd") {
        test("should call IntMath.gcd with absolute values") {
            forAll<Int, Int>(10) { a, b -> a.gcd(b) == IntMath.gcd(a.absoluteValue, b.absoluteValue) }
        }
    }
})
