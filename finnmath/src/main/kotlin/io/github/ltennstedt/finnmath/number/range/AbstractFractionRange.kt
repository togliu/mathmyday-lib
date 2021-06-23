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

package io.github.ltennstedt.finnmath.number.range

import io.github.ltennstedt.finnmath.extension.minus
import io.github.ltennstedt.finnmath.number.fraction.AbstractFraction

/**
 * Base class for ranges of fractions
 *
 * @param N type of the [Number]
 * @param T type of this
 * @param R type of the [ClosedRange]
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public abstract class AbstractFractionRange<
    N : Number,
    T : AbstractFraction<N, T, R>,
    R : ClosedRange<T>
    > : ClosedRange<T> {
    /**
     * Length
     *
     * @since 0.0.1
     */
    public val length: T
        get() = (endInclusive - start).abs()

    public companion object
}
