package domain.entity;

import java.util.Comparator;

/**
 * Kriteria pengurutan transaksi.
 * Setiap opsi membawa comparator-nya sendiri sehingga logika pengurutan
 * terpusat di domain, bukan tersebar sebagai angka ajaib di view/use case.
 */
public enum SortOption {
    /** Urutkan jumlah dari yang terkecil. */
    AMOUNT_ASC(Comparator.comparingInt(Transaction::getAmount)),

    /** Urutkan jumlah dari yang terbesar. */
    AMOUNT_DESC(Comparator.comparingInt(Transaction::getAmount).reversed()),

    /** Tampilkan transaksi pemasukan terlebih dahulu. */
    INCOME_FIRST(Comparator.comparing(Transaction::isIncome).reversed()),

    /** Tampilkan transaksi pengeluaran terlebih dahulu. */
    EXPENSE_FIRST(Comparator.comparing(Transaction::isIncome));

    /** Comparator yang digunakan untuk mengurutkan daftar transaksi. */
    private final Comparator<Transaction> comparator;

    SortOption(Comparator<Transaction> comparator) {
        this.comparator = comparator;
    }

    /** Mengembalikan comparator yang sesuai dengan opsi ini. */
    public Comparator<Transaction> comparator() {
        return comparator;
    }

    /**
     * Memetakan pilihan menu (1-4) ke {@link SortOption} yang sesuai.
     *
     * @return option yang sesuai, atau {@code null} jika pilihan tidak valid
     */
    public static SortOption fromMenuChoice(String choice) {
        return switch (choice) {
            case "1" -> AMOUNT_ASC;
            case "2" -> AMOUNT_DESC;
            case "3" -> INCOME_FIRST;
            case "4" -> EXPENSE_FIRST;
            default -> null;
        };
    }
}
