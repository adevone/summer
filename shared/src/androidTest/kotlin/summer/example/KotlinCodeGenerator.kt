package summer.example

import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.junit.Test
import summer.example.recording.InputStep
import java.io.File

class KotlinCodeGenerator {

    @Test
    fun generate() {
        val pkg = "summer.example.generated"
        val caseName = "reproduce1"
        val stepsJson = """[{"type":"Init","viewModelType":"summer.example.presentation.MainViewModel"},{"type":"Attach","viewModelType":"summer.example.presentation.MainViewModel","viewType":"summer.example.presentation.MainView"},{"type":"Interact","viewModelType":"summer.example.presentation.MainViewModel","inputType":"summer.example.presentation.MainInput.MenuItemClicked","inputValue":{"tab":"Frameworks"}},{"type":"Init","viewModelType":"summer.example.presentation.FrameworksViewModel"},{"type":"Attach","viewModelType":"summer.example.presentation.FrameworksViewModel","viewType":"summer.example.presentation.FrameworksView"},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","inputType":"summer.example.presentation.FrameworksInput.IncreaseClicked","inputValue":{"item":{"framework":{"name":"Spring","version":"5.0"},"quantity":0}}},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","inputType":"summer.example.presentation.FrameworksInput.IncreaseClicked","inputValue":{"item":{"framework":{"name":"Spring","version":"5.0"},"quantity":1}}},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","inputType":"summer.example.presentation.FrameworksInput.IncreaseClicked","inputValue":{"item":{"framework":{"name":"Summer","version":"0.8.17"},"quantity":0}}},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","inputType":"summer.example.presentation.FrameworksInput.DecreaseClicked","inputValue":{"item":{"framework":{"name":"Summer","version":"0.8.17"},"quantity":1}}},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","inputType":"summer.example.presentation.FrameworksInput.CrashClicked","inputValue":{}}]"""
        val steps = Json.decodeFromString(ListSerializer(InputStep.serializer()), stepsJson)
        val kotlinCode = generateKotlinCode(pkg, caseName, steps)
        val outFile = File("./src/androidTest/kotlin/${pkg.replace(".", "/")}/$caseName.kt")
        if (!outFile.exists()) {
            outFile.createNewFile()
        }
        outFile.writeText(kotlinCode)
    }
}