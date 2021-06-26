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

package io.github.ltennstedt.finnmath.linear.builder

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class LongVectorBuilderSpec : FunSpec({
    context("Builder") {
        test("should fill missing indices with 0") {
            val actual = longVector {
                size = 5
                entry {
                    index = 2
                    element = 1L
                }
                entry {
                    index = 4
                    element = 2L
                }
            }
            actual[1] shouldBe 0L
            actual[3] shouldBe 0L
            actual[5] shouldBe 0L
        }
    }
})
