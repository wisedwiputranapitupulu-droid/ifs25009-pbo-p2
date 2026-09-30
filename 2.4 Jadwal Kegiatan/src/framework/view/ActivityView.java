package framework.view;

import adapter.presenter.ActivityPresenter;
import domain.entity.Kegiatan;
import framework.util.InputUtil;
import usecase.ActivityUseCase;

import java.util.NoSuchElementException;

/**
 * Tampilan konsol aplikasi jadwal kegiatan.
 * Menerima input user, memanggil use case, dan menampilkan hasil via presenter.
 * Layer ini tidak mengandung logika bisnis — hanya menangani interaksi user.
 */
public class ActivityView {
    /** Use case yang menjalankan operasi bisnis. */
    private final ActivityUseCase kegiatanUseCase;

    /** Presenter yang memformat hasil ke output layar. */
    private final ActivityPresenter presenter;

    public ActivityView(ActivityUseCase kegiatanUseCase, ActivityPresenter presenter) {
        this.kegiatanUseCase = kegiatanUseCase;
        this.presenter = presenter;
    }

    /** Menampilkan menu utama dan loop interaksi user. */
    public void show() {
        boolean running = true;
        try {
            while (running) {
                presenter.showKegiatan(kegiatanUseCase.getAllKegiatan());
                printMenu();

                String input = InputUtil.input("Pilih");
                switch (input) {
                    case "1" -> addKegiatan();
                    case "2" -> updateKegiatan();
                    case "3" -> searchKegiatan();
                    case "4" -> sortKegiatan();
                    case "5" -> removeKegiatan();
                    case "x" -> running = false;
                    default -> presenter.showInvalidChoice();
                }

                if (running) {
                    System.out.println();
                }
            }
        } catch (NoSuchElementException e) {
            // Input habis (EOF): hentikan program tanpa stack trace.
        }
    }

    /** Mencetak opsi menu ke layar. */
    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah");
        System.out.println("2. Ubah");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    /** Form menambah kegiatan baru. Batal jika judul, hari, ATAU waktu diisi "x". */
    private void addKegiatan() {
        System.out.println("[Menambah Kegiatan]");
        String judul = InputUtil.input("Judul (x Jika Batal)");
        if (judul.equals("x")) {
            return;
        }

        String hari = InputUtil.input("Hari (x Jika Batal)");
        if (hari.equals("x")) {
            return;
        }

        String waktu = InputUtil.input("Waktu (x Jika Batal)");
        if (waktu.equals("x")) {
            return;
        }

        Kegiatan kegiatan = kegiatanUseCase.addKegiatan(judul, hari, waktu);
        presenter.showAddSuccess(kegiatan);
    }

    /** Form mengubah kegiatan berdasarkan ID. Judul/Hari/Waktu baru boleh dikosongkan. */
    private void updateKegiatan() {
        System.out.println("[Mengubah Kegiatan]");
        String strId = InputUtil.input("ID Kegiatan yang diubah (x Jika Batal)");
        if (strId.equals("x")) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        String judulBaru = InputUtil.input("Judul Baru (Kosongkan jika tidak ingin mengubah)");
        String hariBaru = InputUtil.input("Hari Baru (Kosongkan jika tidak ingin mengubah)");
        String waktuBaru = InputUtil.input("Waktu Baru (Kosongkan jika tidak ingin mengubah)");

        judulBaru = judulBaru.isBlank() ? null : judulBaru;
        hariBaru = hariBaru.isBlank() ? null : hariBaru;
        waktuBaru = waktuBaru.isBlank() ? null : waktuBaru;

        if (kegiatanUseCase.updateKegiatan(id, judulBaru, hariBaru, waktuBaru)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    /** Form cari kegiatan berdasarkan judul. */
    private void searchKegiatan() {
        System.out.println("[Mencari Kegiatan]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");

        if (keyword.equals("x")) {
            return;
        }

        presenter.showSearchResults(kegiatanUseCase.searchKegiatan(keyword), keyword);
    }

    /** Form mengurutkan kegiatan berdasarkan pilihan pengurutan. */
    private void sortKegiatan() {
        System.out.println("[Mengurutkan Kegiatan]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Hari (Senin -> Minggu)");
        System.out.println("2. Waktu (Awal -> Akhir)");
        System.out.println("3. Judul (A-Z)");
        System.out.println("4. Judul (Z-A)");
        System.out.println("x. Batal");

        String choice = InputUtil.input("Pilih");
        if (choice.equals("x")) {
            return;
        }

        int option;
        try {
            option = Integer.parseInt(choice.trim());
        } catch (NumberFormatException e) {
            presenter.showInvalidSortChoice();
            return;
        }

        if (option < 1 || option > 4) {
            presenter.showInvalidSortChoice();
            return;
        }

        presenter.showSortedKegiatan(kegiatanUseCase.sortKegiatan(option));
    }

    /** Form hapus kegiatan berdasarkan ID. */
    private void removeKegiatan() {
        System.out.println("[Menghapus Kegiatan]");
        String strId = InputUtil.input("[ID Kegiatan] yang dihapus (x Jika Batal)");

        if (strId.equals("x")) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (kegiatanUseCase.removeKegiatan(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    /** Mem-parsing ID dari string. Menampilkan pesan error dan mengembalikan null jika tidak valid. */
    private Integer parseId(String strId) {
        try {
            return Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showInvalidId();
            return null;
        }
    }
}
