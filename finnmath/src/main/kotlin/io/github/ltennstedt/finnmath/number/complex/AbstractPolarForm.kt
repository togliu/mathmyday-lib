package io.github.ltennstedt.finnmath.number.complex

import com.google.common.annotations.Beta
import org.apiguardian.api.API
import java.io.Serializable

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
    public open val radial: N,
    public open val angular: N
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

    public companion object {
        private const val serialVersionUID = 1L
    }
}
