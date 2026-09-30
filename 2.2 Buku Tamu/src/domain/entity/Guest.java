package domain.entity;

/**
 * Entity inti yang merepresentasikan satu tamu yang berkunjung.
 * Berada di layer domain — tidak bergantung pada layer lain dan bebas dari
 * urusan tampilan maupun penyimpanan.
 */
public class Guest {
    /** ID unik tamu, tidak boleh diubah setelah dibuat. */
    private final int id;

    /** Nama tamu. */
    private final String name;

    /** Tujuan kunjungan tamu. */
    private final String purpose;

    public Guest(int id, String name, String purpose) {
        this.id = id;
        this.name = name;
        this.purpose = purpose;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPurpose() {
        return purpose;
    }
}
