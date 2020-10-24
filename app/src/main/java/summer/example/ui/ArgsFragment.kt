package summer.example.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

abstract class ArgsFragment<TArgs> :
    Fragment(),
    ArgsFragmentFeature<TArgs> {

    @Suppress("LeakingThis")
    override val fragment = this

    override var argsBackingField: TArgs? = null
}

interface ArgsFragmentFeature<TArgs> {

    val argsSerializer: KSerializer<TArgs>
    val fragment: Fragment

    var argsBackingField: TArgs?
    var args: TArgs
        get() {
            if (argsBackingField == null) {
                argsBackingField = json.decodeFromString(argsSerializer, fragment.arguments!!.getString(ARGUMENTS_KEY)!!)
            }
            return argsBackingField!!
        }
        set(args) {
            fragment.arguments = Bundle().apply {
                putString(ARGUMENTS_KEY, json.encodeToString(argsSerializer, args))
            }
            argsBackingField = args
        }

    val json: Json get() = stableJson
}

private val stableJson = Json {}
private const val ARGUMENTS_KEY = "screen_arguments"