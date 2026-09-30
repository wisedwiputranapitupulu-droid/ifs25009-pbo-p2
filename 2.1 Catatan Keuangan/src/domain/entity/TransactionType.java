package domain.entity;

/**
 * Tipe transaksi keuangan.
 */
public enum TransactionType {
    /** Transaksi yang menambah saldo. */
    INCOME,

    /** Transaksi yang mengurangi saldo. */
    EXPENSE;

    /** Label yang ditampilkan ke layar untuk tipe ini. */
    public String label() {
        return this == INCOME ? "Pemasukan" : "Pengeluaran";
    }
}
