package summer.example.recording

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlin.reflect.KClass

@Serializable
data class InputStep(
    val type: Type,
    val viewModelType: String,
    val viewType: String = "",
    val inputName: String = "",
    val inputValue: JsonElement? = null,
) {
    enum class Type {
        Init,
        Attach,
        Detach,
        Interact
    }

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
            inputName: String,
            inputValue: JsonElement,
        ): InputStep {
            return InputStep(type = Type.Interact, viewModelType, "", inputName, inputValue)
        }
    }
}
