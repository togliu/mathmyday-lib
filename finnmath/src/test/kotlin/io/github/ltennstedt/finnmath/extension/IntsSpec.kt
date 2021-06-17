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
import io.kotest.property.PropertyTesting
import io.kotest.property.RandomSource
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.filter
import io.kotest.property.forAll
import kotlin.math.absoluteValue
import kotlin.random.nextInt

val arbOdd = arbitrary { rs: RandomSource ->
    rs.random.nextInt((-10)..10)
}.filter { it % 2 == 1 }

val arbEven = arbitrary { rs: RandomSource ->
    rs.random.nextInt((-10)..10)
}.filter { it % 2 == 0 }

val arbInt = arbitrary { rs: RandomSource ->
    rs.random.nextInt((-10)..10)
}

@Suppress("UNUSED")
object IntExtensionsSpec : FunSpec({
    PropertyTesting.defaultIterationCount = 10
    context("isEven") {
        test("should return false when an Int is odd") {
            forAll(arbOdd) { a ->
                !a.isEven()
            }
        }
        test("should return true when an Int is even") {
            forAll(arbEven) { a ->
                a.isEven()
            }
        }
    }
    context("isOdd") {
        test("should return false when an Int is even") {
            forAll(arbEven) { a ->
                !a.isOdd()
            }
        }
        test("should return true when an Int is odd") {
            forAll(arbOdd) { a ->
                a.isOdd()
            }
        }
    }
    context("gcd") {
        test("should call IntMath.gcd with absolute values") {
            forAll(arbInt, arbInt) { a, b ->
                a.gcd(b) == IntMath.gcd(a.absoluteValue, b.absoluteValue)
            }
        }
    }
})
