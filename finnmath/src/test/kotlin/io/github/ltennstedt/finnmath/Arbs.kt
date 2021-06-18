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

import io.kotest.property.RandomSource
import io.kotest.property.arbitrary.arbitrary
import kotlin.random.nextInt
import kotlin.random.nextLong

val arbInt = arbitrary { rs: RandomSource ->
    rs.random.nextInt((-10)..10)
}
val arbLong = arbitrary { rs: RandomSource ->
    rs.random.nextLong((-10L)..10L)
}
