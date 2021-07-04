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

package io.github.ltennstedt.finnmath.number.fraction

import com.google.common.base.MoreObjects
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import java.math.BigDecimal
import java.util.Objects

class AbstractFractionSpec : FunSpec({
    context("isNotInvertible") {
        test("should be false when a Fraction is invertible") {
            Fraction(1L, 2L).isNotInvertible shouldBe false
        }
        test("should be true when a Fraction is not invertible") {
            Fraction(0L, 2L).isNotInvertible shouldBe true
        }
    }
    context("isNotUnit") {
        test("should be false when a Fraction is a unit") {
            Fraction(1L, 2L).isNotUnit shouldBe false
        }
        test("should be true when a Fraction is not a unit") {
            Fraction(2L, 3L).isNotUnit shouldBe true
        }
    }
    context("isNotDyadic") {
        test("should be false when a Fraction is dyadic") {
            Fraction(1L, 4L).isNotDyadic shouldBe false
        }
        test("should be true when a Fraction is not dyadic") {
            Fraction(1L, 3L).isNotDyadic shouldBe true
        }
    }
    context("isReducible") {
        test("should be false when a Fraction is irreducible") {
            Fraction(2L, 3L).isReducible shouldBe false
        }
        test("should be true when a Fraction is reducible") {
            Fraction(2L, 4L).isReducible shouldBe true
        }
    }
    context("isImproper") {
        test("should be false when a Fraction is proper") {
            Fraction(1L, 2L).isImproper shouldBe false
        }
        test("should be true when a Fraction is improper") {
            Fraction(3L, 2L).isImproper shouldBe true
        }
    }
    context("greaterThanOrEqualTo") {
        test("should be false when a Fraction is less than another one") {
            val fraction = Fraction(1L, 2L)
            val other = Fraction(3L, 2L)
            fraction.greaterThanOrEqualTo(other) shouldBe false
        }
        test("should be true when a Fraction is equivalent to another one") {
            val fraction = Fraction(1L, 2L)
            val other = Fraction(2L, 4L)
            fraction.greaterThanOrEqualTo(other) shouldBe true
        }
        test("should be true when a Fraction is greater than another one") {
            val fraction = Fraction(3L, 2L)
            val other = Fraction(1L, 2L)
            fraction.greaterThanOrEqualTo(other) shouldBe true
        }
    }
    context("min") {
        test("should return self when a Fraction is less than to another one") {
            val fraction = Fraction(1L, 3L)
            val other = Fraction(2L, 3L)
            (fraction min other) shouldBeSameInstanceAs fraction
        }
        test("should return self when a Fraction is equivalent to another one") {
            val fraction = Fraction(1L, 2L)
            val other = Fraction(2L, 4L)
            (fraction min other) shouldBeSameInstanceAs fraction
        }
        test("should return other when a Fraction is less than to another one") {
            val fraction = Fraction(2L, 3L)
            val other = Fraction(1L, 3L)
            (fraction min other) shouldBeSameInstanceAs other
        }
    }
    context("max") {
        test("should return self when a Fraction is greater than to another one") {
            val fraction = Fraction(2L, 3L)
            val other = Fraction(1L, 3L)
            (fraction max other) shouldBeSameInstanceAs fraction
        }
        test("should return self when a Fraction is equivalent to another one") {
            val fraction = Fraction(1L, 2L)
            val other = Fraction(2L, 4L)
            (fraction max other) shouldBeSameInstanceAs fraction
        }
        test("should return other when a Fraction is less than to another one") {
            val fraction = Fraction(1L, 3L)
            val other = Fraction(2L, 3L)
            (fraction max other) shouldBeSameInstanceAs other
        }
    }
    context("equivalent") {
        test("should be false when Fractions are not equivalent to each other") {
            (Fraction(1L, 2L) equivalent Fraction(2L, 3L)) shouldBe false
        }
        test("should be true when Fractions are equivalent to each other") {
            (Fraction(1L, 2L) equivalent Fraction(2L, 4L)) shouldBe true
        }
    }
    context("Fraction extends Number") {
        val fraction = Fraction(2L, 2L)
        test("toByte should return a Fraction as Byte") {
            fraction.toByte() shouldBe 1.toByte()
        }
        test("toShort should return a Fraction as Short") {
            fraction.toShort() shouldBe 1.toShort()
        }
        test("toInt should return a Fraction as Int") {
            fraction.toInt() shouldBe 1
        }
        test("toLong should return a Fraction as Long") {
            fraction.toLong() shouldBe 1L
        }
        test("toFloat should return a Fraction as Float") {
            fraction.toFloat() shouldBe 1.0F
        }
        test("toDouble should return a Fraction as Double") {
            fraction.toDouble() shouldBe 1.0
        }
        test("toBigDecimal should return a Fraction as BigDecimal") {
            fraction.toBigDecimal() shouldBe BigDecimal.ONE
        }
    }
    context("Operators for desctructuring") {
        val fraction = Fraction(2L, 3L)
        test("operator1 should return numerator") {
            fraction.operator1() shouldBe 2L
        }
        test("operator2 should return denominator") {
            fraction.operator2() shouldBe 3L
        }
    }
    test("hashCode should return hash code based on numerator and denominator") {
        Fraction(2L, 3L).hashCode() shouldBe Objects.hash(2L, 3L)
    }
    context("equals") {
        test("should return true when other Fraction is the same") {
            val fraction = Fraction(2L, 3L)
            (fraction == fraction) shouldBe true
        }
        test("should return false when other Fraction has the wrong type") {
            (Fraction(2L, 3L) == Any()) shouldBe false
        }
        test("should return false when numerators are not equal") {
            (Fraction(1L, 3L) == Fraction(2L, 3L)) shouldBe false
        }
        test("should return false when denominators are not equal") {
            (Fraction(1L, 2L) == Fraction(1L, 3L)) shouldBe false
        }
        test("should return true when numerators and denominators are equal") {
            (Fraction(1L, 2L) == Fraction(1L, 2L)) shouldBe true
        }
    }
    test("toString should return a String based on numerator and denominator") {
        val fraction = Fraction(1L, 2L)
        val expected = MoreObjects.toStringHelper(fraction)
            .add("numerator", 1L)
            .add("denominator", 2L)
            .toString()
        fraction.toString() shouldBe expected
    }
})
