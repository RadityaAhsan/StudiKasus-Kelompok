object LibraryManager {
    private val itemRepo = Repository<LibraryItem>()

    fun addItem(item: LibraryItem) {
        itemRepo.add(item)
    }

    fun findByTitle(query: String): List<LibraryItem> =
        itemRepo.findAll { item ->
            item is Searchable && item.matches(query)
        }

    fun borrowItem(itemId: String, memberId: String, date: String): Boolean {
        return runCatching {
            val item = itemRepo.find { it.id == itemId }
                ?: throw ItemNotFoundException("Item dengan ID $itemId tidak ditemukan.")

            val borrowable = item as? Borrowable
                ?: throw IllegalArgumentException(
                    "Item '${item.title}' tidak dapat dipinjam."
                )

            if (!borrowable.isAvailable()) {
                throw ItemAlreadyBorrowedException(
                    "Item '${item.title}' tidak tersedia untuk dipinjam."
                )
            }

            if (!borrowable.borrow(memberId, date)) {
                throw ItemAlreadyBorrowedException(
                    "Item '${item.title}' gagal dipinjam karena tidak tersedia."
                )
            }

            println("Berhasil: '${item.title}' dipinjam oleh $memberId pada $date")
            true
        }.getOrElse { error ->
            println("Gagal meminjam: ${error.message}")
            false
        }
    }

    fun returnItem(itemId: String): Boolean {
        return runCatching {
            val item = itemRepo.find { it.id == itemId }
                ?: throw ItemNotFoundException("Item dengan ID $itemId tidak ditemukan.")

            val borrowable = item as? Borrowable
                ?: throw IllegalArgumentException(
                    "Item '${item.title}' tidak dapat dipinjam."
                )

            if (item.status !is ItemStatus.Borrowed) {
                throw ItemNotBorrowedException(
                    "Item '${item.title}' sedang tidak dipinjam."
                )
            }

            if (!borrowable.returnItem()) {
                throw ItemNotBorrowedException(
                    "Item '${item.title}' tidak berhasil dikembalikan."
                )
            }

            println("Berhasil: '${item.title}' dikembalikan.")
            true
        }.getOrElse { error ->
            println("Gagal mengembalikan: ${error.message}")
            false
        }
    }

    fun getAvailableItems(): List<LibraryItem> =
        itemRepo.findAll { it.status == ItemStatus.Available }

    fun getBorrowedItems(): List<LibraryItem> =
        itemRepo.findAll { it.status is ItemStatus.Borrowed }

    fun displayAllItems() {
        println("=== SISTEM MANAJEMEN PERPUSTAKAAN ===")
        itemRepo.getAll().forEach { it.display() }
    }
}
