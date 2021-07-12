/*
 * Copyright 2017 Lars Tennstedt
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

package io.github.ltennstedt.finnmath.linear.vector

import com.google.common.annotations.Beta
import com.google.common.base.MoreObjects
import io.github.ltennstedt.finnmath.linear.field.Field
import org.apiguardian.api.API
import java.io.Serializable
import java.util.Objects

/**
 * Base class for vectors
 *
 * @param E type of elements
 * @param Q type of quotient of elements
 * @param V type of vector
 * @param N type of taxicab and max norm
 * @param P type of inner product
 * @property entries entries
 * @constructor Constructs an AbstractVector
 * @throws IllegalArgumentException if [entries] is empty
 * @throws IllegalArgumentException if [indices] `!= expectedIndices`
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public abstract class AbstractVector<E : Number, Q : Number, V : AbstractVector<E, Q, V, N, P>, N : Number, P>(
    public val entries: Set<VectorEntry<E>>
) : Serializable {
    /**
     * Indices
     *
     *  @since 0.0.1
     */
    public val indices: Set<Int> by lazy { entries.map { it.index }.toSortedSet() }

    /**
     * Elements
     *
     * @since 0.0.1
     */
    public val elements: List<E> by lazy { entries.map { it.element } }

    /**
     * Size
     *
     * @since 0.0.1
     */
    public val size: Int
        get() = entries.size

    /**
     * Field
     *
     * @since 0.0.1
     */
    protected abstract val field: Field<E, Q, V>

    init {
        require(entries.isNotEmpty()) { "indexToElement expected not to be empty but map = $entries" }
        val expectedIndices = (1..size).toSet()
        require(indices == expectedIndices) {
            "expected indices == expectedIndices but $indices != $expectedIndices"
        }
    }

    /**
     * Returns the sum of this and the [summand]
     *
     * @throws IllegalArgumentException if sizes are not equal
     * @since 0.0.1
     */
    public fun add(summand: V): V {
        require(size == summand.size) { "Equal sizes expected but $size!=${summand.size}" }
        return field.vectorConstructor(entries.map { (i, e) -> VectorEntry(i, field.addition(e, summand[i])) }.toSet())
    }

    /**
     * Returns the difference of this and the [subtrahend]
     *
     * @throws IllegalArgumentException if sizes are not equal
     * @since 0.0.1
     */
    public fun subtract(subtrahend: V): V {
        require(size == subtrahend.size) { "Equal sizes expected but $size!=${subtrahend.size}" }
        return field.vectorConstructor(
            entries.map { (i, e) -> VectorEntry(i, field.subtraction(e, subtrahend[i])) }.toSet()
        )
    }

    /**
     * Returns the dot product of this and the [other one][other]
     *
     * @throws IllegalArgumentException if sizes are not equal
     * @since 0.0.1
     */
    public fun dotProduct(other: V): E {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return entries.map { (i, e) -> field.multiplication(e, other[i]) }.reduce(field.addition)
    }

    /**
     * Returns the scalar product of this and the [scalar]
     *
     * @since 0.0.1
     */
    public fun scalarMultiply(scalar: E): V =
        field.vectorConstructor(entries.map { (i, e) -> VectorEntry(i, field.multiplication(scalar, e)) }.toSet())

    /**
     * Returns the negated [AbstractVector]
     *
     * @since 0.0.1
     */
    public fun negate(): V = scalarMultiply(field.negation(field.one))

    /**
     * Returns if this is orthogonal to [other]
     *
     * @throws IllegalArgumentException if sizes are not equal
     * @since 0.0.1
     */
    public fun orthogonalTo(other: V): Boolean {
        require(size == other.size) { "Equal sizes expected but $size!=${other.size}" }
        return field.equalityByComparing(this * other, field.zero)
    }

    /**
     * Returns the taxicab norm
     *
     * @since 0.0.1
     */
    public abstract fun taxicabNorm(): N

    /**
     * Returns the square of the euclidean norm
     *
     * @since 0.0.1
     */
    public abstract fun euclideanNormPow2(): P

    /**
     * Returns the euclidean norm
     *
     * @since 0.0.1
     */
    public abstract fun euclideanNorm(): N

    /**
     * Returns the maximum norm
     *
     * @since 0.0.1
     */
    public abstract fun maxNorm(): N

    /**
     * Returns the taxicab distance to [other]
     *
     * @throws IllegalArgumentException if sizes are not equal
     * @since 0.0.1
     */
    public fun taxicabDistance(other: V): N {
        require(size == other.size) { "Equal sizes expected but $size != ${other.size}" }
        return (this - other).taxicabNorm()
    }

    /**
     * Returns the euclidean distance to [other]
     *
     * @throws IllegalArgumentException if sizes are not equal
     * @since 0.0.1
     */
    public fun euclideanDistance(other: V): N {
        require(size == other.size) { "Equal sizes expected but $size != ${other.size}" }
        return (this - other).euclideanNorm()
    }

    /**
     * Returns the maximum distance to [other]
     *
     * @throws IllegalArgumentException if sizes are not equal
     * @since 0.0.1
     */
    public fun maxDistance(other: V): N {
        require(size == other.size) { "Equal sizes expected but $size != ${other.size}" }
        return (this - other).maxNorm()
    }

    /**
     * Returns the element at the [index]
     *
     * @throws IllegalArgumentException if [index] !in 1..[size]
     * @since 0.0.1
     */
    public operator fun get(index: Int): E {
        require(index in 1..size) { "index in 1..$size expected but index = $index" }
        return entry(index).element
    }

    /**
     * Returns the [VectorEntry] at the [index]
     *
     * @throws IllegalArgumentException if [index] !in 1..[size]
     * @since 0.0.1
     */
    public fun entry(index: Int): VectorEntry<E> {
        require(index in 1..size) { "index in 1..$size expected but index = $index" }
        return entries.single { it.index == index }
    }

    public operator fun plus(summand: V): V = add(summand)

    public operator fun minus(subtrahend: V): V = subtract(subtrahend)

    public operator fun times(other: V): E = dotProduct(other)

    public operator fun times(scalar: E): V = scalarMultiply(scalar)

    @Suppress("UNCHECKED_CAST")
    public operator fun unaryPlus(): V = this as V

    public operator fun unaryMinus(): V = negate()

    /**
     * Returns if [element] is contained in this
     *
     * @since 0.0.1
     */
    public operator fun contains(element: E): Boolean = element in elements

    /**
     * Returns if this is equal to [other] by comparing fields
     *
     * @throws IllegalArgumentException if sizes are not equal
     * @since 0.0.1
     */
    public fun equalsByComparing(other: V): Boolean {
        require(size == other.size) { "Equal sizes expected but $size != ${other.size}" }
        return entries.all { (i, e) -> field.equalityByComparing(e, other[i]) }
    }

    /**
     * Returns if this is not equal to [other] by comparing fields
     *
     * @throws IllegalArgumentException if sizes are not equal
     * @since 0.0.1
     */
    public fun doesNotEqualByComparing(other: V): Boolean = !equalsByComparing(other)

    override fun hashCode(): Int = Objects.hash(entries)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractVector<*, *, *, *, *>) return false
        return entries == other.entries
    }

    override fun toString(): String = MoreObjects.toStringHelper(this).add("entries", entries).toString()

    public companion object {
        private const val serialVersionUID = 1L
    }
}
