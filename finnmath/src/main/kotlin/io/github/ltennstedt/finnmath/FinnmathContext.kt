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

package io.github.ltennstedt.finnmath

import java.math.MathContext

/**
 * Immutable data class
 *
 * @property scale scale; default argument is 0
 * @property mathContext [MathContext]; default argument is [MathContext.UNLIMITED]
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public data class FinnmathContext @JvmOverloads constructor(
    val scale: Int = 0,
    val mathContext: MathContext = MathContext.UNLIMITED
)
