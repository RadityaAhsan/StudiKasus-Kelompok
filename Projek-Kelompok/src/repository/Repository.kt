class Repository<T : Any> {
    private val items = mutableListOf<T>()

    fun add(item: T) {
        items.add(item)
    }

    fun getAll(): List<T> = items.toList()

    fun find(predicate: (T) -> Boolean): T? =
        items.find(predicate)

    fun findAll(predicate: (T) -> Boolean): List<T> =
        items.filter(predicate)
}
