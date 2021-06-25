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
import io.github.ltennstedt.finnmath.linear.vector.AbstractVector;
import io.github.ltennstedt.finnmath.linear.vector.VectorEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for Java-style vector builders
 *
 * @param <E> type of the elements of the vector
 * @param <V> type of the vector
 * @param <B> type of the builder
 * @author Lars Tennstedt
 * @since 0.0.1
 */
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public abstract class AbstractVectorJavaBuilder<
    E extends Number,
    V extends AbstractVector<E, V, ?, ?>,
    B extends AbstractVectorJavaBuilder<E, V, B>
    > {
    private final @NotNull Map<Integer, E> indexToElement = new HashMap<>();

    private final int size;

    /**
     * Constructor
     *
     * @param size size
     * @throws IllegalArgumentException if {@code size < 1}
     * @since 0.0.1
     */
    protected AbstractVectorJavaBuilder(final int size) {
        checkArgument(size > 0, "size > 0 expected but size = %s", size);
        this.size = size;
    }

    /**
     * Puts the element at the first free index and returns this
     *
     * @param element element
     * @return this
     * @throws NullPointerException if {@code element == null}
     * @throws ArithmeticException if {@code size + 1} overflows
     * @since 0.0.1
     */
    public @NotNull B put(final @NotNull E element) {
        requireNonNull(element, "element");
        final int index = Math.addExact(indexToElement.size(), 1);
        checkArgument(0 < index && index <= size, "0 < index <= size expected but index=%s", index);
        indexToElement.put(index, element);
        @SuppressWarnings("unchecked") final B casted = (B) this;
        return casted;
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
    public @NotNull B put(final int index, final @NotNull E element) {
        checkArgument(0 < index && index <= size, "0 < index <= size expected but index = %s", index);
        requireNonNull(element, "element");
        indexToElement.put(index, element);
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
    public @NotNull B put(final @NotNull VectorEntry<E> entry) {
        requireNonNull(entry, "entry");
        checkArgument(
            0 < entry.getIndex() && entry.getIndex() <= size,
            "0 < entry.index <= size expected but index = %s", entry.getIndex());
        indexToElement.put(entry.getIndex(), entry.getElement());
        @SuppressWarnings("unchecked") final B casted = (B) this;
        return casted;
    }

    /**
     * Computes elements based on the supplier, puts them and returns this
     *
     * @param supplier {@link Supplier}
     * @return this
     * @throws NullPointerException if {@code supplier == null}
     * @since 0.0.1
     */
    public @NotNull B compute(final @NotNull Supplier<E> supplier) {
        requireNonNull(supplier, "supplier");
        for (int i = 1; i <= size; i++) {
            indexToElement.put(i, supplier.get());
        }
        @SuppressWarnings("unchecked") final B casted = (B) this;
        return casted;
    }

    /**
     * Computes elements based on the function, puts them and returns this
     *
     * @param function {@link IntFunction}
     * @return this
     * @throws NullPointerException if {@code function == null}
     * @since 0.0.1
     */
    public @NotNull B compute(final @NotNull IntFunction<E> function) {
        requireNonNull(function, "function");
        for (int i = 1; i <= size; i++) {
            indexToElement.put(i, function.apply(i));
        }
        @SuppressWarnings("unchecked") final B casted = (B) this;
        return casted;
    }

    /**
     * Computes elements if absent based on the supplier, puts them and returns this
     *
     * @param supplier {@link Supplier}
     * @return this
     * @throws NullPointerException if {@code supplier == null}
     * @since 0.0.1
     */
    public @NotNull B computeIfAbsent(final @NotNull Supplier<E> supplier) {
        requireNonNull(supplier, "supplier");
        for (int i = 1; i <= size; i++) {
            indexToElement.putIfAbsent(i, supplier.get());
        }
        @SuppressWarnings("unchecked") final B casted = (B) this;
        return casted;
    }

    /**
     * Computes elements if absent based on the function, puts them and returns this
     *
     * @param function {@link IntFunction}
     * @return this
     * @throws NullPointerException if {@code function == null}
     * @since 0.0.1
     */
    public @NotNull B computeIfAbsent(final @NotNull IntFunction<E> function) {
        requireNonNull(function, "function");
        for (int i = 1; i <= size; i++) {
            indexToElement.putIfAbsent(i, function.apply(i));
        }
        @SuppressWarnings("unchecked") final B casted = (B) this;
        return casted;
    }

    /**
     * Builds and returns the vector
     *
     * @return vector
     * @since 0.0.1
     */
    public abstract @NotNull V build();

    /**
     * Replaces all null values at with 0 and returns this
     *
     * @return this
     * @since 0.0.1
     */
    public abstract @NotNull B nullsToZero();

    /**
     * Size
     *
     * @return size
     * @since 0.0.1
     */
    public int getSize() {
        return size;
    }

    /**
     * indexToElement
     *
     * @return indexToElement
     * @since 0.0.1
     */
    protected @NotNull Map<Integer, E> getIndexToElement() {
        return indexToElement;
    }

    @Override
    public @NotNull String toString() {
        return MoreObjects.toStringHelper(this).add("size", size).add("indexToElement", indexToElement).toString();
    }
}
