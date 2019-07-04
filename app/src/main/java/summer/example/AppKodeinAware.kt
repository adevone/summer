package summer.example

import org.kodein.di.KodeinAware

interface AppKodeinAware : KodeinAware {
    override val kodein get() = App.kodeinAware.kodein
}