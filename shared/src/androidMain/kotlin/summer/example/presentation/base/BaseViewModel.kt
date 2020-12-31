package summer.example.presentation.base

import summer.example.recording.InputStep
import summer.example.recording.steps
import kotlin.reflect.KClass

actual object ViewModelEventsListener {

    actual fun onCreate(clazz: KClass<*>) {
        steps.add(InputStep.init(clazz.qualifiedName!!))
    }

    actual fun onAttach(clazz: KClass<*>, viewClass: KClass<*>) {
        steps.add(InputStep.attach(clazz.qualifiedName!!, viewClass.qualifiedName!!))
    }

    actual fun onDetach(clazz: KClass<*>) {
        steps.add(InputStep.detach(clazz.qualifiedName!!))
    }
}