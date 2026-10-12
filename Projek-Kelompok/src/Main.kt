package src
import Book
import DVD
import Magazine

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

    // TAMBAHAN: Anggota M002 meminjam buku OOP
    println("\n--- Meminjam Buku P002 oleh anggota M002 ---")
    LibraryManager.borrowItem("P002", "M002", "2025-01-16")

    // TAMBAHAN: Anggota M003 meminjam DVD
    println("\n--- Meminjam DVD D001 oleh anggota M003 ---")
    LibraryManager.borrowItem("D001", "M003", "2025-01-17")

    println("\n--- Contoh penanganan error ---")
    LibraryManager.borrowItem("ID-TIDAK-ADA", "M002", "2025-01-16")

    // TAMBAHAN: Skenario gagal logis -> M004 mencoba meminjam buku yang sedang dipinjam M001
    println("\n--- [Gagal] M004 mencoba meminjam Buku P001 yang sedang dipinjam ---")
    LibraryManager.borrowItem("P001", "M004", "2025-01-18")

}
