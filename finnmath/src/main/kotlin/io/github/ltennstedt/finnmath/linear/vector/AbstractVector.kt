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
import org.apiguardian.api.API
import java.io.Serializable
import java.util.Objects

/**
 * Base class for vectors
 *
 * @param E type of the elements of the vector
 * @param V type of the vector
 * @param N type of the taxicab and max norm of the vector
 * @param P type of the inner product
 * @property indexToElement [Map]
 * @constructor Constructs an AbstractVector
 * @throws IllegalArgumentException if [indexToElement] is empty
 * @throws IllegalArgumentException if [indices] `!= expectedIndices`
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public abstract class AbstractVector<E : Number, V : AbstractVector<E, V, N, P>, N, P>(
    protected val indexToElement: Map<Int, E>
) : Serializable {
    /**
     * Indices
     *
     *  @since 0.0.1
     */
    public val indices: Set<Int> by lazy { indexToElement.keys.sorted().toSet() }

    /**
     * Elements
     *
     * @since 0.0.1
     */
    public val elements: List<E> by lazy { indexToElement.toSortedMap().map { it.value } }

    /**
     * Entries
     *
     * @since 0.0.1
     */
    public val entries: Set<VectorEntry<E>> by lazy {
        indexToElement.map { VectorEntry(it.key, it.value) }.sorted().toSet()
    }

    /**
     * Size
     *
     * @since 0.0.1
     */
    public val size: Int get() = indexToElement.size

    init {
        require(indexToElement.isNotEmpty()) { "indexToElement expected not to be empty but map = $indexToElement" }
        val expectedIndices = (1..size).toSet()
        require(indices == expectedIndices) {
            "expected indices == expectedIndices but $indices != $expectedIndices"
        }
    }

    /**
     * Returns the sum of this and the [summand]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public abstract fun add(summand: V): V

    /**
     * Returns the difference of this and the [subtrahend]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public abstract fun subtract(subtrahend: V): V

    /**
     * Returns the dot product of this and the [other one][other]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public abstract fun dotProduct(other: V): E

    /**
     * Returns the scalar product of this and the [scalar]
     *
     * @since 0.0.1
     */
    public abstract fun scalarMultiply(scalar: E): V

    /**
     * Returns the negated [AbstractVector]
     *
     * @since 0.0.1
     */
    public abstract fun negate(): V

    /**
     * Returns if this is orthogonal to [other]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public abstract fun orthogonalTo(other: V): Boolean

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
     * Returns the euclidean distance to [other]
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public fun euclideanDistance(other: V): N {
        require(size == other.size) { "Equal sizes expected but $size != ${other.size}" }
        return (this - other).euclideanNorm()
    }

    /**
     * Returns the maximum distance to [other]
     *
     * @throws IllegalArgumentException sizes are not equal
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
        @Suppress("UNCHECKED_CAST") return indexToElement[index] as E
    }

    /**
     * Returns the [VectorEntry] at the [index]
     *
     * @throws IllegalArgumentException if [index] !in 1..[size]
     * @since 0.0.1
     */
    public fun entry(index: Int): VectorEntry<E> {
        require(index in 1..size) { "index in 1..$size expected but index = $index" }
        @Suppress("UNCHECKED_CAST") return VectorEntry(index, this[index])
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
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public abstract fun equalsByComparing(other: V): Boolean

    /**
     * Returns if this is not equal to [other] by comparing fields
     *
     * @throws IllegalArgumentException sizes are not equal
     * @since 0.0.1
     */
    public fun doesNotEqualByComparing(other: V): Boolean = !equalsByComparing(other)

    override fun hashCode(): Int = Objects.hash(indexToElement)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractVector<*, *, *, *>) return false
        return indexToElement == other.indexToElement
    }

    override fun toString(): String = MoreObjects.toStringHelper(this).add("indexToElement", indexToElement).toString()

    public companion object {
        private const val serialVersionUID = 1L
    }
}
