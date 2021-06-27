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
    private @NotNull IntFunction<E> computationOfAbsent;

    /**
     * Constructor
     *
     * @param size size
     * @param computationOfAbsent computation of absent
     * @throws IllegalArgumentException if {@code size < 1}
     * @throws NullPointerException if {@code computationOfAbsent == null}
     * @since 0.0.1
     */
    protected AbstractVectorJavaBuilder(final int size, final @NotNull IntFunction<E> computationOfAbsent) {
        checkArgument(size > 0, "size > 0 expected but size = %s", size);
        this.size = size;
        this.computationOfAbsent = requireNonNull(computationOfAbsent, "computationOfAbsent");
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
     * Computes elements if absent based on the function, puts them and returns this
     *
     * @param computationOfAbsent {@link IntFunction}
     * @return this
     * @throws NullPointerException if {@code function == null}
     * @since 0.0.1
     */
    public @NotNull B computationOfAbsent(final @NotNull IntFunction<E> computationOfAbsent) {
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
    public abstract @NotNull V build();

    /**
     * indexToElement
     *
     * @return indexToElement
     * @since 0.0.1
     */
    protected @NotNull Map<Integer, E> getIndexToElement() {
        return indexToElement;
    }

    /**
     * Size
     *
     * @return size
     * @since 0.0.1
     */
    protected int getSize() {
        return size;
    }

    /**
     * Computation of absent
     *
     * @return computation of absent
     * @since 0.0.1
     */
    protected @NotNull IntFunction<E> getComputationOfAbsent() {
        return computationOfAbsent;
    }

    @Override
    public @NotNull String toString() {
        return MoreObjects.toStringHelper(this).add("size", size).add("indexToElement", indexToElement).toString();
    }
}
