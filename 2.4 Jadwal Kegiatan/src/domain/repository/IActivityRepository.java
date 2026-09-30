package domain.repository;

import domain.entity.Kegiatan;
import java.util.List;
import java.util.Optional;

/**
 * Port (kontrak) penyimpanan data kegiatan.
 * Interface ini berada di domain agar use case tidak bergantung pada
 * implementasi konkret maupun struktur data yang dipakai untuk menyimpan.
 */
public interface IActivityRepository {
    /** Mengambil semua data kegiatan dari penyimpanan. */
    List<Kegiatan> findAll();

    /** Mencari satu kegiatan berdasarkan ID. Mengembalikan empty jika tidak ditemukan. */
    Optional<Kegiatan> findById(int id);

    /**
     * Menyimpan kegiatan baru. Implementasi bertanggung jawab memberi ID unik.
     *
     * @param judul judul kegiatan
     * @param hari  hari pelaksanaan kegiatan
     * @param waktu waktu pelaksanaan kegiatan
     * @return kegiatan yang tersimpan (lengkap dengan ID)
     */
    Kegiatan save(String judul, String hari, String waktu);

    /**
     * Mengubah data kegiatan berdasarkan ID. Parameter bernilai null berarti
     * field tersebut tidak diubah. Mengembalikan true jika kegiatan ditemukan.
     */
    boolean update(int id, String judul, String hari, String waktu);

    /** Menghapus kegiatan berdasarkan ID. Mengembalikan true jika berhasil. */
    boolean deleteById(int id);
}
