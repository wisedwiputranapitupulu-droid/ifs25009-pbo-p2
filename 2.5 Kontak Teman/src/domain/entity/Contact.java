package domain.entity;

/**
 * Entity inti yang merepresentasikan satu kontak teman.
 * Berada di layer domain — tidak bergantung pada layer lain dan bebas dari
 * urusan tampilan maupun penyimpanan.
 */
public class Contact {
    /** ID unik kontak, tidak boleh diubah setelah dibuat. */
    private final int id;

    /** Nama kontak. Bisa berubah lewat proses "Ubah". */
    private String name;

    /** Nomor telepon kontak. Bisa berubah lewat proses "Ubah". */
    private String phone;

    /** Alamat email kontak. Bisa berubah lewat proses "Ubah". */
    private String email;

    public Contact(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
