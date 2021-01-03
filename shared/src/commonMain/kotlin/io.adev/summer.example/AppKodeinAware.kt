package io.adev.summer.example

import org.kodein.di.DI
import org.kodein.di.DIAware

interface AppKodeinAware : DIAware {
    override val di: DI get() = mainDI
}