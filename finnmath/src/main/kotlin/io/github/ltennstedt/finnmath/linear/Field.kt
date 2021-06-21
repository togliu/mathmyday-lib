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

package io.github.ltennstedt.finnmath.linear

/**
 * Imutable implementation of a field
 *
 * @param N type of the number
 * @param Q type of the quotient
 * @property addition addition
 * @property subtraction subtraction
 * @property multiplication multiplication
 * @property division division
 * @property zero 0
 * @constructor Constructs a MutableField
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public interface Field<N : Number, Q : Number> {
    public val addition: (a: N, b: N) -> N
    public val subtraction: (a: N, b: N) -> N
    public val multiplication: (a: N, b: N) -> N
    public val division: (a: N, b: N) -> Q
    public val zero: N
}
