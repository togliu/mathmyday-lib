/*
 * Copyright 2020 Lars Tennstedt
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

import com.google.common.base.MoreObjects
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry
import pw.forst.katlib.whenNull

/**
 * Base class for vector builders
 *
 * @param E type of the element
 * @param V type of the vector
 * @constructor Constructs an [AbstractVectorBuilder]
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public abstract class AbstractVectorBuilder<E : Number, V> {
    /**
     * Size
     *
     * @since 0.0.1
     */
    public var size: Int = 0

    /**
     * Computation for absent [VectorEntry]
     *
     * @since 0.0.1
     */
    public abstract var computationOfAbsent: (Int) -> E

    /**
     * Entries
     *
     * @since 0.0.1
     */
    protected val entries: MutableList<VectorEntry<E>> = mutableListOf()

    /**
     * Vector constructor
     *
     * @since 0.0.1
     */
    protected abstract val vectorConstructor: (m: Map<Int, E>) -> V

    /**
     * Provides an entry block
     *
     * @since 0.0.1
     */
    public fun entry(init: MutableVectorEntry<E>.() -> Unit): VectorEntry<E> {
        val mutableEntry = MutableVectorEntry<E>()
        mutableEntry.init()
        val entry = mutableEntry.toVectorEntry()
        entries.add(entry)
        return entry
    }

    /**
     * Builds a vector
     *
     * @throws IllegalStateException if entries is empty
     * @throws IllegalStateException if maxIndex > size
     * @throws IllegalStateException if indices are not distinct
     * @since 0.0.1
     */
    public fun build(): V {
        check(entries.isNotEmpty()) { "entries expected not to be empty but entries = $entries}" }
        val indices = entries.map(VectorEntry<E>::index)
        val maxIndex = indices.maxOrNull() as Int
        check(maxIndex <= size) { "maxIndex <= size expected but $maxIndex < $size" }
        val distinctIndices = indices.distinct()
        check(distinctIndices.size == indices.size) {
            "indices.distinct().size == indices.size expected but ${distinctIndices.size} != ${indices.size}"
        }
        for (i in 1..size) {
            entries.filter { it.index == i }.map(VectorEntry<E>::element).singleOrNull().whenNull {
                entries.add(VectorEntry(i, computationOfAbsent(i)))
            }
        }
        entries.sort()
        return vectorConstructor(entries.associate { (i, e) -> i to e })
    }

    override fun toString(): String = MoreObjects.toStringHelper(this)
        .add("size", size)
        .add("entries", entries)
        .add("computationOfAbsent", computationOfAbsent)
        .add("vectorConstructor", vectorConstructor)
        .toString()
}
