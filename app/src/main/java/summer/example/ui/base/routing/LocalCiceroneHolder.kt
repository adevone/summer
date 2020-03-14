package summer.example.ui.base.routing

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import summer.example.entity.Tab

class LocalCiceroneHolder {
    private val containers = mutableMapOf<Tab, Cicerone<Router>>()

    fun getCicerone(type: Tab): Cicerone<Router> {
        if (!containers.containsKey(type)) {
            containers[type] = Cicerone.create()
        }
        return containers[type]!!
    }
}
