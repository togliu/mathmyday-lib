package io.github.ltennstedt.finnmath.extension

import io.github.ltennstedt.finnmath.kotest.number.shouldBeInvertible
import io.github.ltennstedt.finnmath.number.LongFraction
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.checkAll

val invertibleArb = arbitrary { rs ->
    val numerator = Arb.int(1..10).next(rs)
    val denominator = Arb.int(1..10).next(rs)
    LongFraction(numerator, denominator)
}

@Suppress("UNUSED")
object LongFractionMatchersSpec : FunSpec({
    context("shouldBeInvertible") {
        test("should not fail when LongFraction is invertible") {
            checkAll<LongFraction>(invertibleArb) { it.shouldBeInvertible() }
        }
    }
})
