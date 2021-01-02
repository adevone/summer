package summer.example.ui.base

import androidx.appcompat.app.AppCompatActivity
import summer.DidSetMixin
import summer.example.AppKodeinAware

abstract class BaseActivity : AppCompatActivity(), AppKodeinAware {
    companion object : DidSetMixin
}