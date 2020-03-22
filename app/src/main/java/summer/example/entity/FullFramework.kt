package summer.example.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FullFramework(
    val name: String,
    val version: String
) : Parcelable

fun Framework.toFull() = FullFramework(name, version)