package io.adev.summer.example.ui.base

import androidx.appcompat.app.AppCompatActivity
import summer.DidSetMixin
import io.adev.summer.example.AppKodeinAware

abstract class BaseActivity : AppCompatActivity(), AppKodeinAware {
    companion object : DidSetMixin
}