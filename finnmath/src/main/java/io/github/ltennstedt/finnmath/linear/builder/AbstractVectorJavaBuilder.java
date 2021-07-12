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

package io.github.ltennstedt.finnmath.linear.builder;

import static com.google.common.base.Preconditions.checkArgument;
import static java.util.Objects.requireNonNull;

import com.google.common.annotations.Beta;
import com.google.common.base.MoreObjects;
import io.github.ltennstedt.finnmath.linear.field.Field;
import io.github.ltennstedt.finnmath.linear.vector.AbstractVector;
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry;
import java.util.HashSet;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for Java-style vector builders
 *
 * @param <E> type of elements
 * @param <Q> type of quotient of elements
 * @param <V> type of vector
 * @param <B> type of builder
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public abstract class AbstractVectorJavaBuilder<
    E extends Number,
    Q extends Number,
    V extends AbstractVector<E, Q, V, ?, ?>,
    B extends AbstractVectorJavaBuilder<E, Q, V, B>
    > {
    private final @NotNull Set<VectorEntry<E>> entries = new HashSet<>();
    private final int size;
    private @NotNull IntFunction<E> computationOfAbsent = i -> getField().getZero();

    /**
     * Constructor
     *
     * @param size size
     * @throws IllegalArgumentException if {@code size < 1}
     * @since 0.0.1
     */
    @SuppressWarnings("java:S1192")
    protected AbstractVectorJavaBuilder(final int size) {
        checkArgument(size > 0, "size > 0 expected but size = %s", size);
        this.size = size;
    }

    /**
     * Puts the element at the index and returns this
     *
     * @param index index
     * @param element element
     * @return this
     * @throws IllegalArgumentException if {@code indey < 1 || index > size}
     * @throws NullPointerException if {@code element == null}
     */
    public final @NotNull B set(final int index, final @NotNull E element) {
        checkArgument(0 < index && index <= size, "0 < index <= size expected but index = %s", index);
        requireNonNull(element, "element");
        entries.add(new VectorEntry<>(index, element));
        @SuppressWarnings("unchecked") final B casted = (B) this;
        return casted;
    }

    /**
     * Puts the entry and returns this
     *
     * @param entry {@link VectorEntry}
     * @return this
     * @throws NullPointerException if {@code entry == null}
     * @throws IllegalArgumentException if {@code entry.index < 1 || entry.index > size}
     * @since 0.0.1
     */
    public final @NotNull B set(final @NotNull VectorEntry<E> entry) {
        requireNonNull(entry, "entry");
        checkArgument(
            0 < entry.getIndex() && entry.getIndex() <= size,
            "0 < entry.index <= size expected but index = %s", entry.getIndex());
        entries.add(entry);
        @SuppressWarnings("unchecked") final B casted = (B) this;
        return casted;
    }

    /**
     * Computes elements if absent based on the function, puts them and returns this
     *
     * @param computationOfAbsent {@link IntFunction}
     * @return this
     * @throws NullPointerException if {@code function == null}
     * @since 0.0.1
     */
    public final @NotNull B computationOfAbsent(final @NotNull IntFunction<E> computationOfAbsent) {
        this.computationOfAbsent = requireNonNull(computationOfAbsent, "computationOfAbsent");
        @SuppressWarnings("unchecked") final B casted = (B) this;
        return casted;
    }

    /**
     * Builds and returns the vector
     *
     * @return vector
     * @since 0.0.1
     */
    public final @NotNull V build() {
        IntStream.rangeClosed(1, size).forEach(i -> {
            if (entries.stream().noneMatch(e -> e.getIndex() == i)) {
                entries.add(new VectorEntry<>(i, computationOfAbsent.apply(i)));
            }
        });
        return getField().getVectorConstructor().invoke(entries);
    }

    /**
     * Field
     *
     * @return field
     * @since 0.0.1
     */
    protected abstract @NotNull Field<E, Q, V> getField();

    @Override
    public final @NotNull String toString() {
        return MoreObjects.toStringHelper(this)
            .add("size", size)
            .add("entries", entries)
            .add("computationOfAbsent", computationOfAbsent)
            .toString();
    }
}
