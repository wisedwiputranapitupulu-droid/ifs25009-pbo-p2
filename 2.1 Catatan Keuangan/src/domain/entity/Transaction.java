package domain.entity;

/**
 * Entity inti yang merepresentasikan satu transaksi keuangan.
 * Berada di layer domain — tidak bergantung pada layer lain dan bebas dari
 * urusan tampilan maupun penyimpanan.
 */
public class Transaction {
    /** ID unik transaksi, tidak boleh diubah setelah dibuat. */
    private final int id;

    /** Keterangan atau deskripsi transaksi. */
    private final String description;

    /** Jumlah nominal transaksi, selalu bernilai positif. */
    private final int amount;

    /** Tipe transaksi: pemasukan atau pengeluaran. */
    private final TransactionType type;

    public Transaction(int id, String description, int amount, TransactionType type) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    /** True jika transaksi ini bertipe pemasukan. */
    public boolean isIncome() {
        return type == TransactionType.INCOME;
    }

    /**
     * Nilai bertanda transaksi terhadap saldo:
     * positif untuk pemasukan, negatif untuk pengeluaran.
     */
    public int signedAmount() {
        return isIncome() ? amount : -amount;
    }
}
