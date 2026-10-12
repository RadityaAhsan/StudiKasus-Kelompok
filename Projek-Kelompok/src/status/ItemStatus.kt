sealed class ItemStatus {
    object Available : ItemStatus()
    data class Borrowed(val by: String, val date: String) : ItemStatus()
    object Lost : ItemStatus()

    fun display(): String = when (this) {
        Available -> "Tersedia"
        is Borrowed -> "Dipinjam oleh ${this.by} pada ${this.date}"
        Lost -> "Hilang"
    }
}
