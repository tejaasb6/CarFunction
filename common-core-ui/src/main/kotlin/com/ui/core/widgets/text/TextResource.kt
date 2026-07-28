package com.ui.core.widgets.text

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString

/**
 * Type-safe text content abstraction for the [Text] widget.
 *
 * Supports static strings, Android string resources (translatable),
 * and annotated strings — all through a unified API using the `.TR`
 * extension property.
 *
 * ```kotlin
 * // Static string
 * TextState(text = "Hello".TR)
 *
 * // Android string resource (translatable)
 * TextState(text = R.string.hello.TR)
 *
 * // String resource with interpolation
 * TextState(text = R.string.greeting.TR("World"))
 *
 * // Annotated string (styled spans)
 * TextState(text = buildAnnotatedString { append("Bold "); pushStyle(...) }.TR)
 * ```
 */
@Immutable
sealed interface TextResource {
    /** Resolves this resource to an [AnnotatedString] at composition time. */
    @get:Composable
    val annotated: AnnotatedString
}

/** A [TextResource] wrapping a static or pre-built [AnnotatedString]. */
@JvmInline
value class TextResourceAnnotated(
    val text: AnnotatedString,
) : TextResource {
    override val annotated: AnnotatedString
        @Composable get() = text

    override fun toString(): String = text.toString()
}

/** A [TextResource] wrapping an Android `@StringRes` resource ID. */
@JvmInline
value class TextResourceId(
    @StringRes val id: Int,
) : TextResource {
    override val annotated: AnnotatedString
        @Composable get() = AnnotatedString(stringResource(id = id))
}

/**
 * A [TextResource] wrapping an Android `@StringRes` resource ID with
 * interpolation parameters.
 */
data class TextResourceIdWithInterpolation(
    @StringRes val id: Int,
    val interpolateParams: Array<out Any>,
) : TextResource {
    @Suppress("SpreadOperator")
    override val annotated: AnnotatedString
        @Composable get() = AnnotatedString(stringResource(id = id, *interpolateParams))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TextResourceIdWithInterpolation) return false
        return id == other.id && interpolateParams.contentEquals(other.interpolateParams)
    }

    override fun hashCode(): Int = 31 * id + interpolateParams.contentHashCode()
}

/** Empty text resource constant. */
val EmptyTextResource: TextResource = "".TR

// ── Extension properties ───────────────────────────────────────────────────────

/** Converts a [String] to a [TextResource]. */
val String.TR: TextResource
    get() = TextResourceAnnotated(AnnotatedString(this))

/** Converts an [AnnotatedString] to a [TextResource]. */
val AnnotatedString.TR: TextResource
    get() = TextResourceAnnotated(this)

/** Converts an Android `@StringRes` resource ID to a [TextResource]. */
val @receiver:StringRes Int.TR: TextResource
    get() = TextResourceId(this)

/** Converts an Android `@StringRes` resource ID with params to a [TextResource]. */
@Suppress("FunctionName")
fun @receiver:StringRes Int.TR(vararg interpolateParams: Any): TextResource = TextResourceIdWithInterpolation(this, interpolateParams)
