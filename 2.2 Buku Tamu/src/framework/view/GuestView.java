package framework.view;

import adapter.presenter.GuestPresenter;
import domain.entity.Guest;
import framework.util.InputUtil;
import usecase.GuestUseCase;

/**
 * Tampilan konsol aplikasi buku tamu.
 * Menerima input user, memanggil use case, dan menampilkan hasil via presenter.
 * Layer ini tidak mengandung logika bisnis — hanya menangani interaksi user.
 */
public class GuestView {
    /** Use case yang menjalankan operasi bisnis. */
    private final GuestUseCase guestUseCase;

    /** Presenter yang memformat hasil ke output layar. */
    private final GuestPresenter presenter;

    public GuestView(GuestUseCase guestUseCase, GuestPresenter presenter) {
        this.guestUseCase = guestUseCase;
        this.presenter = presenter;
    }

    /** Menampilkan menu utama dan loop interaksi user. */
    public void show() {
        boolean running = true;
        while (running) {
            presenter.showGuests(guestUseCase.getAllGuests());
            printMenu();

            String input = InputUtil.input("Pilih");
            switch (input) {
                case "1" -> registerGuest();
                case "2" -> searchGuest();
                case "3" -> removeGuest();
                case "x" -> running = false;
                default -> presenter.showInvalidChoice();
            }

            if (running) {
                System.out.println();
            }
        }
    }

    /** Mencetak opsi menu ke layar. */
    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Daftarkan");
        System.out.println("2. Cari");
        System.out.println("3. Hapus");
        System.out.println("x. Keluar");
    }

    /** Form pendaftaran tamu baru. Batal jika nama ATAU tujuan diisi "x". */
    private void registerGuest() {
        System.out.println("[Mendaftarkan Tamu]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (name.equals("x")) {
            return;
        }

        String purpose = InputUtil.input("Tujuan Kunjungan (x Jika Batal)");
        if (purpose.equals("x")) {
            return;
        }

        Guest guest = guestUseCase.registerGuest(name, purpose);
        presenter.showRegisterSuccess(guest);
    }

    /** Form cari tamu berdasarkan nama. */
    private void searchGuest() {
        System.out.println("[Mencari Tamu]");
        String keyword = InputUtil.input("Nama (x Jika Batal)");

        if (keyword.equals("x")) {
            return;
        }

        presenter.showSearchResults(guestUseCase.searchGuests(keyword), keyword);
    }

    /** Form hapus tamu berdasarkan ID. */
    private void removeGuest() {
        System.out.println("[Menghapus Tamu]");
        String strId = InputUtil.input("[ID Tamu] yang dihapus (x Jika Batal)");

        if (strId.equals("x")) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (guestUseCase.removeGuest(id)) {
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
