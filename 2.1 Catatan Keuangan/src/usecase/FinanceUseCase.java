package usecase;

import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import domain.repository.ITransactionRepository;
import java.util.List;

/**
 * Use case yang menangani logika bisnis aplikasi catatan keuangan.
 * Tidak melakukan I/O — hanya memproses data dan mengembalikan hasil
 * ke layer presenter/view.
 */
public class FinanceUseCase {
    /** Port repository yang di-inject dari luar (Dependency Inversion). */
    private final ITransactionRepository transactionRepository;

    public FinanceUseCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /** Mengambil semua transaksi yang tersedia. */
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /** Menambahkan transaksi baru dan mengembalikan entity yang tersimpan. */
    public Transaction addTransaction(String description, int amount, TransactionType type) {
        return transactionRepository.save(description, amount, type);
    }

    /** Menghapus transaksi berdasarkan ID. */
    public boolean removeTransaction(int id) {
        return transactionRepository.deleteById(id);
    }

    /** Mencari transaksi yang keterangannya mengandung kata kunci (case-insensitive). */
    public List<Transaction> searchTransactions(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return transactionRepository.findAll().stream()
                .filter(transaction -> transaction.getDescription().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    /** Mengurutkan transaksi sesuai kriteria {@link SortOption} yang dipilih. */
    public List<Transaction> sortTransactions(SortOption option) {
        return transactionRepository.findAll().stream()
                .sorted(option.comparator())
                .toList();
    }

    /** Menghitung saldo saat ini (total pemasukan − total pengeluaran). */
    public int getBalance() {
        return transactionRepository.findAll().stream()
                .mapToInt(Transaction::signedAmount)
                .sum();
    }
}
