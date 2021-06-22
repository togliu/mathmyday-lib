package io.github.ltennstedt.finnmath.number

/**
 * Base class for polar forms
 *
 * @param N type of the comparable number
 * @param T type of the polar form
 * @param C type of the complex number
 * @property radial radial
 * @property angular angular
 * @constructor Constructs a AbstractPolarForm
 * @author Lars Tennstedt
 * @since 0.0.1
 */
public abstract class AbstractPolarForm<N : Comparable<N>, T : AbstractPolarForm<N, T, C>, C>(
    public open val radial: N,
    public open val angular: N
) {
    /**
     * Returns the corresponding [C] of [this][T]
     *
     * @since 0.0.1
     */
    public abstract fun toComplexNumber(): C

    /**
     * Returns if [this][T] is equal to the [other one][other] by comparing coordinates
     *
     * @since 0.0.1
     */
    public fun equalsByComparing(other: T): Boolean =
        radial.compareTo(other.radial) == 0 && angular.compareTo(other.angular) == 0

    /**
     * Returns if [this][T] is not equal to the [other one][other] by comparing coordinates
     *
     * @since 0.0.1
     */
    public fun doesNotequalsByComparing(other: T): Boolean = !equalsByComparing(other)
}
