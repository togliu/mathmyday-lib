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

/**
 * Mutable implementation of a vector entry
 *
 * @param E type of the element
 * @property index index
 * @property element element
 * @constructor Constructs a MutableVectorEntry
 * @throws IllegalArgumentException if [index] < 1
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class MutableVectorEntry<E : Number> {
    public var index: Int = 0
    public var element: E? = null

    public fun toVectorEntry(): VectorEntry<E> = VectorEntry(index, element as E)

    override fun toString(): String =
        MoreObjects.toStringHelper(this).add("index", index).add("element", element).toString()
}
