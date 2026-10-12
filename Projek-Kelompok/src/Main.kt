fun main() {
    LibraryManager.addItem(
        Book("P001", "Pemrograman Kotlin", 2023, "Budi Santoso", 350)
    )
    LibraryManager.addItem(
        Book("P002", "Dasar-Dasar OOP", 2022, "Siti Rahayu", 280)
    )
    LibraryManager.addItem(
        Magazine("M001", "Tech Today", 2024, 42, "Tech Media")
    )
    LibraryManager.addItem(
        DVD("D001", "Film Dokumenter", 2021, 120, "Dokumenter")
    )

    LibraryManager.displayAllItems()

    println("\n--- Meminjam Buku P001 oleh anggota M001 ---")
    LibraryManager.borrowItem("P001", "M001", "2025-01-15")

    println("\n--- Mencari 'Kotlin' ---")
    LibraryManager.findByTitle("Kotlin").forEach { it.display() }

    println("\n--- Mengembalikan Buku P001 ---")
    LibraryManager.returnItem("P001")

    println("\n--- Item Tersedia ---")
    LibraryManager.getAvailableItems().forEach { it.display() }

    println("\n--- Item Dipinjam ---")
    LibraryManager.getBorrowedItems().forEach { it.display() }

    println("\n--- Contoh penanganan error ---"  )
    LibraryManager.borrowItem("ID-TIDAK-ADA", "M002", "2025-01-16")

    println("\n--- Skenario tambahan ---")

    // Pinjam item yang tidak ada
    runCatching {
        val result = LibraryManager.borrowItem("D999", "M003", "2025-02-01")
        if (!result) throw ItemNotFoundException("D999")
    }.onFailure { println("ERROR: ${it.message}") }

    // Pinjam item yang sudah dipinjam (M001 sudah pinjam P001)
    runCatching {
        val result = LibraryManager.borrowItem("P001", "M003", "2025-02-01")
        if (!result) throw ItemAlreadyBorrowedException("P001")
    }.onFailure { println("ERROR: ${it.message}") }

    // Kembalikan item yang tidak sedang dipinjam
    runCatching {
        val result = LibraryManager.returnItem("M001")
        if (!result) throw ItemNotBorrowedException("M001")
    }.onFailure { println("ERROR: ${it.message}") }
}
