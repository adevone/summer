package summer.example.recording

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class InputStep(
    val type: Type,
    val viewModelType: String,
    val viewType: String = "",
    val methodName: String = "",
    val arguments: List<Argument> = emptyList()
) {
    enum class Type {
        Init,
        Attach,
        Detach,
        Interact
    }

    @Serializable
    data class Argument(
        val name: String,
        val value: JsonElement? = null,
        val isHidden: Boolean = false
    )

    companion object {

        fun init(viewModelType: String): InputStep {
            return InputStep(type = Type.Init, viewModelType)
        }

        fun attach(viewModelType: String, viewType: String): InputStep {
            return InputStep(type = Type.Attach, viewModelType, viewType)
        }

        fun detach(viewModelType: String): InputStep {
            return InputStep(type = Type.Detach, viewModelType)
        }

        fun interact(
            viewModelType: String,
            methodName: String,
            arguments: List<Argument>
        ): InputStep {
            return InputStep(type = Type.Interact, viewModelType, "", methodName, arguments)
        }
    }
}