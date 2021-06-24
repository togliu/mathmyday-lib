/*
 * Copyright 2019 Lars Tennstedt
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

import java.io.Serializable

/**
 * Immutable implementation of a vector entry
 *
 * @param E type of the element
 * @property index index
 * @property element element
 * @constructor Constructs a [VectorEntry]
 * @throws IllegalArgumentException if [index] < 1
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public data class VectorEntry<E : Number>(val index: Int, val element: E) : Comparable<VectorEntry<E>>, Serializable {
    init {
        require(index > 0) { "expected index > 0 but index = $index" }
    }

    override fun compareTo(other: VectorEntry<E>): Int = COMPARATOR.compare(this, other)

    public companion object {
        /**
         * [Comparator]
         */
        @JvmField
        public val COMPARATOR: Comparator<VectorEntry<*>> = compareBy { it.index }

        private const val serialVersionUID = 1L
    }
}
