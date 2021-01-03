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

    val hiddenArgSteps = steps
        .groupBy { step ->
            step.viewModelType
        }
        .flatMap { (_, viewModelSteps) ->
            viewModelSteps
                .groupBy { viewModelStep ->
                    viewModelStep.methodName
                }
                .mapNotNull { (_, methodSteps) ->
                    val methodStep = methodSteps.firstOrNull()
                    if (methodStep?.hasHiddenArgs() == true) {
                        "${methodStep.callMaskedMethodName()}: ${methodStep.callMaskedMethodType()}"
                    } else {
                        null
                    }
                }
        }

    val viewSteps = steps.filter { it.viewType.isNotBlank() }.associateBy { it.viewType }.values
    val formattedViews = viewSteps.map { step ->
        "${step.viewProviderName()}: ${providerType(step.viewType + "?")} = { null }"
    }

    val formattedArgs = (formattedModels + hiddenArgSteps + formattedViews)
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
                append(indent(level = 1))
                val viewModelVarName = varName(step.viewModelType)
                if (!step.hasHiddenArgs()) {
                    append("${viewModelVarName}.${step.methodName}")
                    if (step.arguments.isNotEmpty()) {
                        append("(\n")
                        val formattedArguments = step.arguments.joinToString(separator = ",\n") { argument ->
                            val argJson = Json.encodeToString(argument.value)
                            "${indent(level = 2)}decode(\"\"\"${argJson}\"\"\")"
                        }
                        append(formattedArguments)
                        append("\n")
                        append(indent(level = 1))
                        append(")")
                    } else {
                        append("()")
                    }
                } else {
                    val arguments = listOf(viewModelVarName) + step.arguments.mapNotNull { argument ->
                        if (!argument.isHidden) {
                            val argJson = Json.encodeToString(argument.value)
                            "\"\"\"$argJson\"\"\""
                        } else {
                            null
                        }
                    }
                    val formattedArguments = arguments.joinToString(separator = ",\n") { arg ->
                        "${indent(level = 2)}$arg"
                    }
                    append("${step.callMaskedMethodName()}(\n")
                    append("${formattedArguments}\n")
                    append(indent(level = 1))
                    append(")")
                }
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

fun InputStep.callMaskedMethodName(): String {
    return "call${methodName.capitalize()}Of${simpleName(viewModelType)}"
}

fun InputStep.callMaskedMethodType(): String {
    val formattedArguments = arguments.mapNotNull { argument ->
        if (!argument.isHidden) {
            "String"
        } else {
            null
        }
    }
    val types = (listOf(viewModelType) + formattedArguments).joinToString()
    return "($types) -> Unit"
}

fun providerType(type: String): String {
    return "() -> $type"
}

fun InputStep.hasHiddenArgs(): Boolean {
    return this.arguments.any { it.isHidden }
}