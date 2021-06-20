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

import io.github.ltennstedt.finnmath.number.Fraction
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs

class AbstractFractionsSpec : FunSpec({
    val a = Fraction(1L, 2L)
    val b = Fraction(2L, 3L)
    test("plus should delegate to add") {
        (a + b) shouldBe Fraction(7L, 6L)
    }
    test("minus should delegate to subtract") {
        (a - b) shouldBe Fraction(-1L, 6L)
    }
    test("times should delegate to multiply") {
        (a * b) shouldBe Fraction(2L, 6L)
    }
    test("div should delegate to divide") {
        (a / b) shouldBe Fraction(3L, 4L)
    }
    test("unaryPlus should be this") {
        +a shouldBeSameInstanceAs a
    }
    test("unaryMinus should delegate to negate") {
        (-a) shouldBe a.negate()
    }
})
