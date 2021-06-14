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

import io.github.ltennstedt.finnmath.number.LongFraction
import io.github.ltennstedt.finnmath.number.LongFractionRange
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

object ClosedRangeExtensionTest : FunSpec({
    test("isNotEmpty should return false when range is empty") {
        val actual = LongFractionRange.EMPTY.isNotEmpty()

        actual shouldBe false
    }
    test("isNotEmpty should return true when range is empty") {
        val range = LongFractionRange(LongFraction.ZERO, LongFraction.ONE)

        val actual = range.isNotEmpty()

        actual shouldBe true
    }
})
