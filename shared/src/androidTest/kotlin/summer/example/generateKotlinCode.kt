package summer.example

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import summer.example.recording.InputStep
import java.util.*

fun generateKotlinCode(
    pkg: String,
    caseName: String,
    steps: List<InputStep>
): String = buildString {

    fun indent(level: Int): String = buildString {
        repeat(level) {
            append("    ")
        }
    }

    append("package $pkg\n\n")

    append("import summer.example.recording.decode\n\n")

    val viewModelSteps = steps.associateBy { it.viewModelType }.values
    val formattedModels = viewModelSteps.map { step ->
        "${step.viewModelProviderName()}: ${providerType(step.viewModelType)}"
    }

    val viewSteps = steps.filter { it.viewType.isNotBlank() }.associateBy { it.viewType }.values
    val formattedViews = viewSteps.map { step ->
        "${step.viewProviderName()}: ${providerType(step.viewType + "?")} = { null }"
    }

    val formattedArgs = (formattedModels + formattedViews)
        .joinToString(separator = ",\n") { arg ->
            "${indent(level = 1)}${arg}"
        }
    append("fun $caseName(\n${formattedArgs}\n) {\n")

    viewModelSteps.forEach { step ->
        append(indent(level = 1))
        append("lateinit var ${varName(step.viewModelType)}: ${step.viewModelType}\n")
    }

    steps.forEach { step ->
        append("\n")

        when (step.type) {
            InputStep.Type.Init -> {
                append(indent(level = 1))
                append("${varName(step.viewModelType)} = ${step.viewModelProviderName()}()")
            }
            InputStep.Type.Attach -> {
                append("${indent(level = 1)}${varName(step.viewModelType)}.getView = {\n")
                append("${indent(level = 2)}${step.viewProviderName()}()\n")
                append("${indent(level = 1)}}\n")
                append("${indent(level = 1)}${varName(step.viewModelType)}.viewCreated()")
            }
            InputStep.Type.Detach -> {
                append("${indent(level = 1)}${varName(step.viewModelType)}.getView = { null }\n")
            }
            InputStep.Type.Interact -> {
                val viewModelVarName = varName(step.viewModelType)
                append(indent(level = 1), "${viewModelVarName}.pass(", "\n")
                val inputJson = Json.encodeToString(step.inputValue)
                append(indent(level = 2), "decode(\"\"\"${inputJson}\"\"\")", "\n")
                append(indent(level = 1), ")", "\n")
            }
        }
    }

    append("\n}\n\n")
}

fun simpleName(type: String): String {
    return type.substringAfterLast(".")
}

fun varName(type: String): String {
    return simpleName(type).decapitalize(Locale.getDefault())
}

fun InputStep.viewModelProviderName(): String {
    return "create${simpleName(viewModelType)}"
}

fun InputStep.viewProviderName(): String {
    return "createViewFor${simpleName(viewModelType)}"
}

fun providerType(type: String): String {
    return "() -> $type"
}