@file:Suppress("unused")

package summer.example.recording

import kotlinx.serialization.json.Json.Default.encodeToJsonElement
import kotlinx.serialization.serializer
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import summer.example.presentation.base.Hide
import kotlin.reflect.full.createType

@Aspect
class AspectLogging {

    @Pointcut("execution(* *..BaseViewModel.*(..))")
    fun isBaseModel() {

    }

    @Pointcut("execution(* *..*.getViewProxy(..)) || execution(* *..*.viewProxy(..))")
    fun viewProxy() {

    }

    @Pointcut("execution(public * summer.example.presentation..set(..))")
    fun isSet() {

    }

    @Pointcut("isSet() || viewProxy() || isBaseModel()")
    fun base() {

    }

    @Pointcut("execution(* summer.example.presentation.base.BaseViewModel+.*(..))")
    fun isViewModel() {

    }

    @Pointcut("execution(public * summer.example.presentation..*(..))")
    fun onEvent() {

    }

    @Before("onEvent() && isViewModel() && !base()")
    fun onViewModelEvent(joinPoint: JoinPoint?) {
        if (joinPoint?.getThis() != null) {
            val signature = joinPoint.signature as MethodSignature
            val parameterTypes: Array<Class<*>> = signature.method.parameterTypes
            val methodName: String = signature.method.name
            val methodAnnotations: Array<Array<Annotation>> = joinPoint.target::class.java
                .getMethod(methodName, *parameterTypes)
                .parameterAnnotations
            val serializedArguments = joinPoint.args.mapIndexed { argIndex, arg ->
                val argSerializer = serializer(arg::class.createType())
                val argValue = encodeToJsonElement(argSerializer, arg)
                val argAnnotations = methodAnnotations.getOrNull(argIndex) ?: emptyArray()
                val isHidden = argAnnotations.any { it is Hide }
                InputStep.Argument(
                    name = signature.parameterNames[argIndex],
                    value = argValue.takeIf { !isHidden },
                    isHidden
                )
            }
            steps.add(
                InputStep.interact(
                    viewModelType = joinPoint.target::class.qualifiedName!!,
                    methodName = signature.name,
                    arguments = serializedArguments
                )
            )
        }
    }
}

var steps = mutableListOf<InputStep>()