package usecase;

import domain.entity.Kegiatan;
import domain.repository.IActivityRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Use case yang menangani logika bisnis aplikasi jadwal kegiatan.
 * Tidak melakukan I/O — hanya memproses data dan mengembalikan hasil
 * ke layer presenter/view.
 */
public class ActivityUseCase {
    /** Urutan hari dalam satu minggu, dipakai untuk pengurutan berdasarkan hari. */
    private static final List<String> URUTAN_HARI = List.of(
            "senin", "selasa", "rabu", "kamis", "jumat", "sabtu", "minggu");

    /** Port repository yang di-inject dari luar (Dependency Inversion). */
    private final IActivityRepository kegiatanRepository;

    public ActivityUseCase(IActivityRepository kegiatanRepository) {
        this.kegiatanRepository = kegiatanRepository;
    }

    /** Mengambil semua kegiatan yang terdaftar. */
    public List<Kegiatan> getAllKegiatan() {
        return kegiatanRepository.findAll();
    }

    /** Menambahkan kegiatan baru dan mengembalikan entity yang tersimpan. */
    public Kegiatan addKegiatan(String judul, String hari, String waktu) {
        return kegiatanRepository.save(judul, hari, waktu);
    }

    /**
     * Mengubah data kegiatan berdasarkan ID.
     * Parameter bernilai null berarti field tersebut tidak diubah.
     *
     * @return true jika kegiatan ditemukan (dan field yang tidak null diperbarui)
     */
    public boolean updateKegiatan(int id, String judul, String hari, String waktu) {
        return kegiatanRepository.update(id, judul, hari, waktu);
    }

    /** Menghapus kegiatan berdasarkan ID. */
    public boolean removeKegiatan(int id) {
        return kegiatanRepository.deleteById(id);
    }

    /** Mencari kegiatan yang judulnya mengandung kata kunci (case-insensitive). */
    public List<Kegiatan> searchKegiatan(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return kegiatanRepository.findAll().stream()
                .filter(kegiatan -> kegiatan.getJudul().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    /**
     * Mengembalikan salinan daftar kegiatan yang sudah diurutkan, tanpa mengubah
     * urutan data asli di penyimpanan.
     *
     * @param option 1 = Hari (Senin -> Minggu, lalu waktu terurut), 2 = Waktu (Awal -> Akhir),
     *               3 = Judul (A-Z), 4 = Judul (Z-A)
     */
    public List<Kegiatan> sortKegiatan(int option) {
        List<Kegiatan> items = new ArrayList<>(kegiatanRepository.findAll());

        Comparator<Kegiatan> comparator = switch (option) {
            case 1 -> Comparator
                    .comparingInt(this::indexOfHari)
                    .thenComparing(Kegiatan::getWaktu);
            case 2 -> Comparator.comparing(Kegiatan::getWaktu);
            case 3 -> Comparator.comparing(Kegiatan::getJudul, String.CASE_INSENSITIVE_ORDER);
            case 4 -> Comparator.comparing(Kegiatan::getJudul, String.CASE_INSENSITIVE_ORDER).reversed();
            default -> null;
        };

        if (comparator != null) {
            items.sort(comparator);
        }
        return items;
    }

    /** Menentukan urutan hari (0 = Senin, ..., 6 = Minggu) untuk keperluan pengurutan. */
    private int indexOfHari(Kegiatan kegiatan) {
        int index = URUTAN_HARI.indexOf(kegiatan.getHari().toLowerCase());
        return index == -1 ? URUTAN_HARI.size() : index;
    }
}
