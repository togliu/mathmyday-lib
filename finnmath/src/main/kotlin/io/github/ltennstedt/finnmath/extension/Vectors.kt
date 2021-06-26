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

package io.github.ltennstedt.finnmath.extension

import io.github.ltennstedt.finnmath.linear.vector.AbstractVector

public operator fun <E : Number, V : AbstractVector<E, V, N, P>, N, P> AbstractVector<E, V, N, P>.plus(summand: V): V =
    add(summand)

public operator fun <E : Number, V : AbstractVector<E, V, N, P>, N, P> AbstractVector<E, V, N, P>.minus(
    subtrahend: V
): V = subtract(subtrahend)

public operator fun <E : Number, V : AbstractVector<E, V, N, P>, N, P> AbstractVector<E, V, N, P>.times(other: V): E =
    dotProduct(other)

public operator fun <E : Number, V : AbstractVector<E, V, N, P>, N, P> AbstractVector<E, V, N, P>.times(scalar: E): V =
    scalarMultiply(scalar)
