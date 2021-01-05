package io.adev.summer.example.ui.base

import androidx.appcompat.app.AppCompatActivity
import summer.DidSetMixin

abstract class BaseActivity : AppCompatActivity() {
    companion object : DidSetMixin
}