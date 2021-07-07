package io.github.ltennstedt.finnmath.number.complex

import com.google.common.annotations.Beta
import com.google.common.base.MoreObjects
import org.apiguardian.api.API
import java.io.Serializable
import java.util.Objects

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
@API(status = API.Status.EXPERIMENTAL, since = "0.0.1")
@Beta
public abstract class AbstractPolarForm<N : Comparable<N>, T : AbstractPolarForm<N, T, C>, C>(
    public val radial: N,
    public val angular: N
) : Serializable {
    /**
     * Returns this as complex number
     *
     * @since 0.0.1
     */
    public abstract fun toComplexNumber(): C

    /**
     * Returns if this is equal to the [other one][other] by comparing coordinates
     *
     * @since 0.0.1
     */
    public fun equalsByComparing(other: T): Boolean =
        radial.compareTo(other.radial) == 0 && angular.compareTo(other.angular) == 0

    /**
     * Returns if this is not equal to the [other one][other] by comparing coordinates
     *
     * @since 0.0.1
     */
    public fun doesNotequalsByComparing(other: T): Boolean = !equalsByComparing(other)

    override fun hashCode(): Int = Objects.hash(radial, angular)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractPolarForm<*, *, *>) return false
        return radial == other.radial && angular == other.angular
    }

    override fun toString(): String =
        MoreObjects.toStringHelper(this).add("radial", radial).add("angular", angular).toString()

    public companion object {
        private const val serialVersionUID = 1L
    }
}
