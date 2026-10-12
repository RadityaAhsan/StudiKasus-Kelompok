class Magazine(
    override val id: String,
    override val title: String,
    override val year: Int,
    val issueNumber: Int,
    val publisher: String
) : LibraryItem(id, title, year), Searchable {
    override var status: ItemStatus = ItemStatus.Available

    override fun getDetails(): String =
        "Majalah edisi #$issueNumber, diterbitkan oleh $publisher"

    override fun matches(query: String): Boolean =
        title.contains(query, ignoreCase = true) ||
        publisher.contains(query, ignoreCase = true)
}
