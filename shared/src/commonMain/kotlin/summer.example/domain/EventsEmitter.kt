package summer.example.domain

abstract class EventsEmitter<TListener> {

    var listener: TListener? = null
    open fun subscribe(listener: TListener) {
        this.listener = listener
    }

    open fun unsubscribe(listener: TListener) {
        if (listener == this.listener) {
            this.listener = null
        }
    }
}