@file:Suppress("unused")

package summer.example.recording

import kotlinx.serialization.json.Json.Default.encodeToJsonElement
import kotlinx.serialization.serializer
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.CodeSignature
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
    fun onCreateAdvice(joinPoint: JoinPoint?) {
        if (joinPoint?.getThis() != null) {
            val signature = joinPoint.signature as CodeSignature
            val serializedArguments = joinPoint.args.mapIndexed { argIndex, arg ->
                val argSerializer = serializer(arg::class.createType())
                val argValue = encodeToJsonElement(argSerializer, arg)
                InputStep.Argument(
                    name = signature.parameterNames[argIndex],
                    argValue
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