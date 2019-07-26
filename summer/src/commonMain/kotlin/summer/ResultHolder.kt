package summer

abstract class ResultHolder<T> {

    private var value: T? = null

    fun put(value: T) {
        this.value = value
    }

    fun pop() = value?.also {
        value = null
    }
}