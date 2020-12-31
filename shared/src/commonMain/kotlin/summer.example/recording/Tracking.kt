package summer.example.recording

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import summer.example.entity.About
import summer.example.entity.Framework
import summer.example.entity.Tab
import summer.example.presentation.base.BaseViewModel
import summer.example.presentation.base.Hidden
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private val module = SerializersModule {
    contextual(About.serializer())
    contextual(Framework.serializer())
    contextual(Tab.serializer())
}

fun tracking(action: () -> Unit) = Tracking.A0(action)
fun <A1> tracking(action: (A1) -> Unit) = Tracking.A1(action)
fun <A1, A2> tracking(action: (A1, A2) -> Unit) = Tracking.A2(action)

abstract class Tracking<TAction> : ReadOnlyProperty<BaseViewModel<*>, TAction> {

    class A0(
        private val action: () -> Unit
    ) : Tracking<() -> Unit>() {
        override fun getValue(thisRef: BaseViewModel<*>, property: KProperty<*>): () -> Unit {
            return {
                addStep(
                    viewModel = thisRef,
                    property,
                    arguments = emptyList()
                )
                action()
            }
        }
    }

    class A1<A1>(
        private val action: (A1) -> Unit
    ) : Tracking<(A1) -> Unit>() {
        override fun getValue(thisRef: BaseViewModel<*>, property: KProperty<*>): (A1) -> Unit {
            return { a1 ->
                addStep(
                    viewModel = thisRef,
                    property,
                    arguments = listOf(stepArg(a1))
                )
                action(a1)
            }
        }
    }

    class A2<A1, A2>(
        private val action: (A1, A2) -> Unit
    ) : Tracking<(A1, A2) -> Unit>() {
        override fun getValue(thisRef: BaseViewModel<*>, property: KProperty<*>): (A1, A2) -> Unit {
            return { a1, a2 ->
                addStep(
                    viewModel = thisRef,
                    property,
                    arguments = listOf(stepArg(a1), stepArg(a2))
                )
                action(a1, a2)
            }
        }
    }

    protected fun addStep(
        viewModel: BaseViewModel<*>,
        property: KProperty<*>,
        arguments: List<InputStep.Argument>
    ) {
        steps.add(
            InputStep.interact(
                viewModelType = viewModel::class.qualifiedName!!,
                methodName = property.name,
                arguments = arguments
            )
        )
    }

    protected fun <TArg : Any> stepArg(arg: TArg?): InputStep.Argument {

        if (arg is Hidden<*>?) {
            return InputStep.Argument(
                value = null,
                isHidden = true
            )
        }

        val argSerializer: KSerializer<TArg>? = if (arg != null)
            module.getContextual(arg::class) as? KSerializer<TArg>
        else
            null

        if (arg == null || argSerializer == null) {
            return InputStep.Argument(
                value = JsonNull,
                isHidden = false
            )
        }

        val argValue = Json.encodeToJsonElement<TArg>(argSerializer, arg)
        return InputStep.Argument(
            value = argValue,
            isHidden = false
        )
    }
}