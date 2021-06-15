package io.github.ltennstedt.finnmath.number

import io.github.ltennstedt.finnmath.kotest.number.shouldBeInvertible
import io.kotest.core.spec.style.FunSpec
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.int
import io.kotest.property.arbitrary.next
import io.kotest.property.checkAll

val invertibleArb = arbitrary { rs ->
    val numerator = Arb.int(1..10).next(rs)
    val denominator = Arb.int(1..10).next(rs)
    Fraction(numerator, denominator)
}

@Suppress("UNUSED")
object LongFractionMatchersSpec : FunSpec({
    context("shouldBeInvertible") {
        test("should not fail when LongFraction is invertible") {
            checkAll(invertibleArb) { it.shouldBeInvertible() }
        }
    }
})
