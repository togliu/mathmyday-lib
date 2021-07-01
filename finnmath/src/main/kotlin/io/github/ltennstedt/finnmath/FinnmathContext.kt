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

import com.google.common.annotations.Beta
import com.google.errorprone.annotations.Immutable
import org.apiguardian.api.API
import java.io.Serializable
import java.math.MathContext

/**
 * Immutable data class holding a [scale] and a [mathContext]
 *
 * @property scale scale; default argument is 0
 * @property mathContext [MathContext]; default argument is [MathContext.UNLIMITED]
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
@Immutable
public data class FinnmathContext @JvmOverloads constructor(
    val scale: Int = 0,
    val mathContext: MathContext = MathContext.UNLIMITED
) : Serializable {
    public companion object {
        private const val serialVersionUID = 1L
    }
}
