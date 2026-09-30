package adapter.repository;

import domain.entity.Kegiatan;
import domain.repository.IActivityRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementasi repository menggunakan penyimpanan in-memory berbasis {@link List}.
 * Berada di layer adapter — mengimplementasikan port dari domain sekaligus
 * menyembunyikan detail struktur data dari layer di atasnya.
 */
public class ActivityRepository implements IActivityRepository {
    /** Penyimpanan data kegiatan di memori. */
    private final List<Kegiatan> data = new ArrayList<>();

    /** Penghitung ID otomatis, bertambah setiap kali kegiatan baru disimpan. */
    private int idCounter = 0;

    @Override
    public List<Kegiatan> findAll() {
        // Salinan defensif agar data internal tidak bisa diubah dari luar
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Kegiatan> findById(int id) {
        return data.stream()
                .filter(kegiatan -> kegiatan.getId() == id)
                .findFirst();
    }

    @Override
    public Kegiatan save(String judul, String hari, String waktu) {
        Kegiatan kegiatan = new Kegiatan(nextId(), judul, hari, waktu);
        data.add(kegiatan);
        return kegiatan;
    }

    @Override
    public boolean update(int id, String judul, String hari, String waktu) {
        Optional<Kegiatan> kegiatan = findById(id);
        if (kegiatan.isEmpty()) {
            return false;
        }

        Kegiatan k = kegiatan.get();
        if (judul != null) {
            k.setJudul(judul);
        }
        if (hari != null) {
            k.setHari(hari);
        }
        if (waktu != null) {
            k.setWaktu(waktu);
        }
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(kegiatan -> kegiatan.getId() == id);
    }

    /** Menghasilkan ID unik berikutnya. */
    private int nextId() {
        return ++idCounter;
    }
}
