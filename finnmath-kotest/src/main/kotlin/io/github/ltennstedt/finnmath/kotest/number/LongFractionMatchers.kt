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

package io.github.ltennstedt.finnmath.kotest.number

import io.github.ltennstedt.finnmath.number.LongFraction
import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.shouldBe

public fun invertible(): Matcher<LongFraction> = object : Matcher<LongFraction> {
    override fun test(value: LongFraction): MatcherResult = MatcherResult(
        value.isInvertible,
        "LongFraction $value should be invertible",
        "LongFraction $value should not be invertible"
    )
}

public fun LongFraction.shouldBeInvertible(): Unit = this shouldBe invertible()
