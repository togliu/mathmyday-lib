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

import io.github.ltennstedt.finnmath.linear.field.BigIntegerField
import io.github.ltennstedt.finnmath.linear.vector.BigIntegerVector
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry
import java.math.BigInteger

/**
 * Provides BigIntegerVector block
 *
 * @since 0.0.1
 */
public fun bigIntegerVector(init: BigIntegerVectorBuilder.() -> Unit): BigIntegerVector {
    val builder = BigIntegerVectorBuilder()
    builder.init()
    return builder.build()
}

/**
 * Builder for [BigIntegerVectors][BigIntegerVector]
 *
 * @constructor Constructs a BigIntegerVectorBuilder
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public class BigIntegerVectorBuilder : AbstractVectorBuilder<BigInteger, BigIntegerVector>() {
    override var computationOfAbsent: (Int) -> BigInteger = { _ -> BigInteger.ZERO }
    override val vectorConstructor: (s: Set<VectorEntry<BigInteger>>) -> BigIntegerVector
        get() = BigIntegerField.vectorConstructor
}
