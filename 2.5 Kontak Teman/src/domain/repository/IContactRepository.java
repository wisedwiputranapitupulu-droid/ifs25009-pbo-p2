package domain.repository;

import domain.entity.Contact;
import java.util.List;
import java.util.Optional;

/**
 * Port (kontrak) penyimpanan data kontak.
 * Interface ini berada di domain agar use case tidak bergantung pada
 * implementasi konkret maupun struktur data yang dipakai untuk menyimpan.
 */
public interface IContactRepository {
    /** Mengambil semua data kontak dari penyimpanan. */
    List<Contact> findAll();

    /** Mencari satu kontak berdasarkan ID. Mengembalikan empty jika tidak ditemukan. */
    Optional<Contact> findById(int id);

    /**
     * Menyimpan kontak baru. Implementasi bertanggung jawab memberi ID unik.
     *
     * @param name  nama kontak
     * @param phone nomor telepon kontak
     * @param email alamat email kontak
     * @return kontak yang tersimpan (lengkap dengan ID)
     */
    Contact save(String name, String phone, String email);

    /**
     * Mengubah data kontak berdasarkan ID. Parameter bernilai null berarti
     * field tersebut tidak diubah. Mengembalikan true jika kontak ditemukan.
     */
    boolean update(int id, String name, String phone, String email);

    /** Menghapus kontak berdasarkan ID. Mengembalikan true jika berhasil. */
    boolean deleteById(int id);
}
