package summer

/**
 * Обрабатывает ошибки в слое Presenter'ов
 * если они не были обработаны с помощью tryDo
 */
interface ExceptionsHandler {
    fun handle(e: Throwable)
}