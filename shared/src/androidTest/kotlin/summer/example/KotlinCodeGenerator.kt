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
        val stepsJson = """[{"type":"Init","viewModelType":"summer.example.presentation.MainViewModel"},{"type":"Attach","viewModelType":"summer.example.presentation.MainViewModel","viewType":"summer.example.presentation.MainView"},{"type":"Interact","viewModelType":"summer.example.presentation.MainViewModel","methodName":"onMenuItemClick","arguments":[{"name":"tab","value":"Frameworks"}]},{"type":"Init","viewModelType":"summer.example.presentation.FrameworksViewModel"},{"type":"Attach","viewModelType":"summer.example.presentation.FrameworksViewModel","viewType":"summer.example.presentation.FrameworksView"},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","methodName":"onIncreaseClick","arguments":[{"name":"item","value":{"framework":{"name":"Spring","version":"5.0"},"quantity":0}}]},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","methodName":"onIncreaseClick","arguments":[{"name":"item","value":{"framework":{"name":"Spring","version":"5.0"},"quantity":1}}]},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","methodName":"onIncreaseClick","arguments":[{"name":"item","value":{"framework":{"name":"Summer","version":"0.8.17"},"quantity":0}}]},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","methodName":"onDecreaseClick","arguments":[{"name":"item","value":{"framework":{"name":"Summer","version":"0.8.17"},"quantity":1}}]},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","methodName":"onItemClick","arguments":[{"name":"password","isHidden":true},{"name":"item","value":{"framework":{"name":"Summer","version":"0.8.17"},"quantity":0}}]},{"type":"Detach","viewModelType":"summer.example.presentation.FrameworksViewModel"},{"type":"Init","viewModelType":"summer.example.presentation.FrameworkDetailsViewModel"},{"type":"Attach","viewModelType":"summer.example.presentation.FrameworkDetailsViewModel","viewType":"summer.example.presentation.FrameworkDetailsView"},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworkDetailsViewModel","methodName":"init","arguments":[{"name":"initialFramework","value":{"name":"Summer","version":"0.8.17"}}]},{"type":"Detach","viewModelType":"summer.example.presentation.FrameworkDetailsViewModel"},{"type":"Attach","viewModelType":"summer.example.presentation.FrameworksViewModel","viewType":"summer.example.presentation.FrameworksView"},{"type":"Interact","viewModelType":"summer.example.presentation.FrameworksViewModel","methodName":"onCrashClick"}]"""
        val steps = Json.decodeFromString(ListSerializer(InputStep.serializer()), stepsJson)
        val kotlinCode = generateKotlinCode(pkg, caseName, steps)
        val outFile = File("./src/androidTest/kotlin/${pkg.replace(".", "/")}/$caseName.kt")
        if (!outFile.exists()) {
            outFile.createNewFile()
        }
        outFile.writeText(kotlinCode)
    }
}