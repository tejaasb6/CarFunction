package com.ui.audi.engine.validator

import com.ui.audi.engine.model.DesignToken
import com.ui.audi.engine.model.ShadowDesignToken
import com.ui.audi.engine.model.TypographyDesignToken

/**
 * Validates a loaded Audi theme for completeness and correctness.
 * Brand-specific: validation rules (required prefixes, color formats) may differ per brand.
 */
class ThemeValidator {
    data class ValidationResult(
        val isValid: Boolean,
        val errors: List<String>,
        val warnings: List<String>,
    )

    companion object {
        private val REFERENCE_PATTERN = Regex("\\{([^}]+)\\}")
        private val HEX_PATTERN = Regex("^#[0-9A-Fa-f]{3,8}$")
        private val HSLA_PATTERN = Regex("^hsla?\\(")
        val REQUIRED_PREFIXES = listOf("Core.Color", "Sem.Color")
    }

    fun validate(
        tokenMap: Map<String, DesignToken>,
        typographyTokens: Map<String, TypographyDesignToken> = emptyMap(),
        shadowTokens: Map<String, ShadowDesignToken> = emptyMap(),
    ): ValidationResult {
        val errors = mutableListOf<String>()
        val warnings = mutableListOf<String>()

        // Build a unified set of all known token paths so that cross-map
        // references (e.g. Cmp tokenMap → Sem typographyTokens/shadowTokens)
        // are correctly recognised as resolved during validation.
        val allKnownPaths = tokenMap.keys + typographyTokens.keys + shadowTokens.keys

        validateRequiredSets(tokenMap, errors)
        validateReferences(tokenMap, allKnownPaths, errors)
        validateNoCircularReferences(tokenMap, errors)
        validateColorFormats(tokenMap, warnings)
        validateTypographyReferences(typographyTokens, tokenMap, errors)
        validateShadowReferences(shadowTokens, tokenMap, errors)

        return ValidationResult(isValid = errors.isEmpty(), errors = errors, warnings = warnings)
    }

    private fun validateRequiredSets(
        tokenMap: Map<String, DesignToken>,
        errors: MutableList<String>,
    ) {
        REQUIRED_PREFIXES.forEach { prefix ->
            if (tokenMap.keys.none { it.startsWith(prefix) }) {
                errors.add("Required token set missing: no tokens found with prefix '$prefix'")
            }
        }
    }

    private fun validateReferences(
        tokenMap: Map<String, DesignToken>,
        allKnownPaths: Set<String>,
        errors: MutableList<String>,
    ) {
        tokenMap.forEach { (path, token) ->
            REFERENCE_PATTERN.findAll(token.rawValue).forEach { ref ->
                val refPath = ref.groupValues[1]
                if (refPath !in allKnownPaths) {
                    errors.add("Unresolved reference in '$path': {$refPath} not found")
                }
            }
        }
    }

    private fun validateNoCircularReferences(
        tokenMap: Map<String, DesignToken>,
        errors: MutableList<String>,
    ) {
        val visited = mutableSetOf<String>()
        val inStack = mutableSetOf<String>()
        tokenMap.keys.forEach { path ->
            if (path !in visited) detectCycle(path, tokenMap, visited, inStack, errors)
        }
    }

    private fun detectCycle(
        path: String,
        tokenMap: Map<String, DesignToken>,
        visited: MutableSet<String>,
        inStack: MutableSet<String>,
        errors: MutableList<String>,
    ) {
        visited.add(path)
        inStack.add(path)
        tokenMap[path]?.let { token ->
            REFERENCE_PATTERN.findAll(token.rawValue).forEach { ref ->
                val refPath = ref.groupValues[1]
                when {
                    refPath in inStack -> errors.add("Circular reference: $path -> $refPath")
                    refPath !in visited && refPath in tokenMap ->
                        detectCycle(refPath, tokenMap, visited, inStack, errors)
                }
            }
        }
        inStack.remove(path)
    }

    private fun validateColorFormats(
        tokenMap: Map<String, DesignToken>,
        warnings: MutableList<String>,
    ) {
        tokenMap.filter { it.value.type == "color" }.forEach { (path, token) ->
            val raw = token.rawValue
            if (!raw.contains("{") && !HEX_PATTERN.matches(raw) && !HSLA_PATTERN.containsMatchIn(raw)) {
                warnings.add("Potentially invalid color in '$path': $raw")
            }
        }
    }

    @Suppress("NestedBlockDepth")
    private fun validateTypographyReferences(
        typographyTokens: Map<String, TypographyDesignToken>,
        tokenMap: Map<String, DesignToken>,
        errors: MutableList<String>,
    ) {
        typographyTokens.forEach { (path, typo) ->
            listOf(typo.fontFamily, typo.fontWeight, typo.fontSize, typo.lineHeight, typo.letterSpacing)
                .forEach { field ->
                    REFERENCE_PATTERN.findAll(field).forEach { ref ->
                        val refPath = ref.groupValues[1]
                        if (!tokenMap.containsKey(refPath)) {
                            errors.add("Unresolved typography reference in '$path': {$refPath} not found")
                        }
                    }
                }
        }
    }

    @Suppress("NestedBlockDepth")
    private fun validateShadowReferences(
        shadowTokens: Map<String, ShadowDesignToken>,
        tokenMap: Map<String, DesignToken>,
        errors: MutableList<String>,
    ) {
        shadowTokens.forEach { (path, shadow) ->
            shadow.layers.forEach { layer ->
                REFERENCE_PATTERN.findAll(layer.color).forEach { ref ->
                    val refPath = ref.groupValues[1]
                    if (!tokenMap.containsKey(refPath)) {
                        errors.add("Unresolved shadow reference in '$path': {$refPath} not found")
                    }
                }
            }
        }
    }
}
