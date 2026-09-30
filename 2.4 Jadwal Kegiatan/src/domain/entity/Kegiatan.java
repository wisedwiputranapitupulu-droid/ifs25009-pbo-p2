package domain.entity;

/**
 * Entity inti yang merepresentasikan satu kegiatan dalam jadwal.
 * Berada di layer domain — tidak bergantung pada layer lain dan bebas dari
 * urusan tampilan maupun penyimpanan.
 */
public class Kegiatan {
    /** ID unik kegiatan, tidak boleh diubah setelah dibuat. */
    private final int id;

    /** Judul kegiatan. Bisa berubah lewat proses "Ubah". */
    private String judul;

    /** Hari pelaksanaan kegiatan (mis. "Senin"). Bisa berubah lewat proses "Ubah". */
    private String hari;

    /** Waktu pelaksanaan kegiatan (mis. "08:00"). Bisa berubah lewat proses "Ubah". */
    private String waktu;

    public Kegiatan(int id, String judul, String hari, String waktu) {
        this.id = id;
        this.judul = judul;
        this.hari = hari;
        this.waktu = waktu;
    }

    public int getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
