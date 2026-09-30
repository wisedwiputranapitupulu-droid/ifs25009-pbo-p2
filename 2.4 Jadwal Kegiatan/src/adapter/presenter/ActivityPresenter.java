package adapter.presenter;

import domain.entity.Kegiatan;
import java.util.List;

/**
 * Presenter yang memformat data dari use case menjadi output layar.
 * Memisahkan logika tampilan dari entity, use case, dan view sehingga
 * format output bisa diubah tanpa menyentuh domain.
 */
public class ActivityPresenter {

    /** Memformat satu kegiatan menjadi baris teks untuk ditampilkan. */
    private String format(Kegiatan kegiatan) {
        return String.format("%d | %s | %s | %s", kegiatan.getId(), kegiatan.getJudul(),
                kegiatan.getHari(), kegiatan.getWaktu());
    }

    /** Helper umum untuk menampilkan daftar kegiatan. */
    private void printList(List<Kegiatan> items, String header, String emptyMessage) {
        System.out.println(header);

        if (items.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }

        for (Kegiatan kegiatan : items) {
            System.out.println(format(kegiatan));
        }
    }

    /** Menampilkan daftar seluruh kegiatan yang terdaftar. */
    public void showKegiatan(List<Kegiatan> items) {
        printList(items, "Daftar Kegiatan:", "- Data kegiatan belum tersedia!");
    }

    /** Menampilkan hasil pencarian berdasarkan kata kunci. */
    public void showSearchResults(List<Kegiatan> items, String keyword) {
        printList(items, "Hasil Pencarian: \"" + keyword + "\"", "- Kegiatan tidak ditemukan!");
    }

    /** Menampilkan daftar kegiatan yang sudah diurutkan. */
    public void showSortedKegiatan(List<Kegiatan> items) {
        printList(items, "Daftar Kegiatan (Terurut):", "- Data kegiatan belum tersedia!");
    }

    /** Menampilkan pesan sukses setelah menambah kegiatan. */
    public void showAddSuccess(Kegiatan kegiatan) {
        System.out.printf("Berhasil menambah kegiatan: %s%n", format(kegiatan));
    }

    /** Menampilkan pesan sukses setelah mengubah kegiatan. */
    public void showUpdateSuccess() {
        System.out.println("Berhasil mengubah kegiatan.");
    }

    /** Menampilkan pesan gagal saat mengubah kegiatan. */
    public void showUpdateFailed(int id) {
        System.out.printf("[!] Gagal mengubah kegiatan dengan ID: %d.%n", id);
    }

    /** Menampilkan pesan sukses setelah menghapus kegiatan. */
    public void showRemoveSuccess() {
        System.out.println("Berhasil menghapus kegiatan.");
    }

    /** Menampilkan pesan gagal saat menghapus kegiatan. */
    public void showRemoveFailed(int id) {
        System.out.printf("[!] Gagal menghapus kegiatan dengan ID: %d.%n", id);
    }

    /** Menampilkan pesan saat pilihan menu utama tidak dikenali. */
    public void showInvalidChoice() {
        System.out.println("[!] Pilihan tidak dimengerti.");
    }

    /** Menampilkan pesan saat pilihan pengurutan tidak valid. */
    public void showInvalidSortChoice() {
        System.out.println("[!] Pilihan tidak valid!");
    }

    /** Menampilkan pesan saat ID yang dimasukkan tidak valid. */
    public void showInvalidId() {
        System.out.println("[!] ID tidak valid!");
    }
}
