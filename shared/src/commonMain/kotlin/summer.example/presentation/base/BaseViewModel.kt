package summer.example.presentation.base

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import summer.ArchViewModel
import summer.example.AppKodeinAware
import summer.example.entity.About
import summer.example.entity.Framework
import summer.example.entity.Tab
import summer.example.presentation.FrameworkDetailsInput
import summer.example.presentation.FrameworksInput
import summer.example.presentation.MainInput
import summer.example.recording.InputStep
import summer.example.recording.steps
import kotlin.reflect.KClass

private val module = SerializersModule {
    contextual(About.serializer())
    contextual(Framework.serializer())
    contextual(Tab.serializer())
    contextual(FrameworkDetailsInput.Init.serializer())
    contextual(FrameworksInput.ItemClicked.serializer())
    contextual(FrameworksInput.IncreaseClicked.serializer())
    contextual(FrameworksInput.DecreaseClicked.serializer())
    contextual(FrameworksInput.CrashClicked.serializer())
    contextual(MainInput.MenuItemClicked.serializer())
}

abstract class BaseViewModel<TView, TInput : Any> : CoroutinesViewModel<TView>() {

    init {
        ViewModelEventsListener.onCreate(this::class)
    }

    fun pass(input: TInput) {
        addStep(input)
        handle(input)
    }

    protected abstract fun handle(input: TInput)

    private fun addStep(input: TInput) {
        val inputSerializer: KSerializer<TInput> = module.getContextual(input::class) as KSerializer<TInput>
        val argValue = Json.encodeToJsonElement(inputSerializer, input)
        steps.add(
            InputStep.interact(
                viewModelType = this::class.qualifiedName!!,
                inputName = input::class.qualifiedName!!,
                inputValue = argValue
            )
        )
    }
}

val Any?.exhaustive get() = Unit

expect abstract class CoroutinesViewModel<TView>() :
    ArchViewModel<TView>,
    AppKodeinAware

expect object ViewModelEventsListener {
    fun onCreate(clazz: KClass<*>)
    fun onAttach(clazz: KClass<*>, viewClass: KClass<*>)
    fun onDetach(clazz: KClass<*>)
}