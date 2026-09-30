package framework.view;

import adapter.presenter.ContactPresenter;
import domain.entity.Contact;
import framework.util.InputUtil;
import usecase.ContactUseCase;

/**
 * Tampilan konsol aplikasi kontak teman.
 * Menerima input user, memanggil use case, dan menampilkan hasil via presenter.
 * Layer ini tidak mengandung logika bisnis — hanya menangani interaksi user.
 */
public class ContactView {
    /** Use case yang menjalankan operasi bisnis. */
    private final ContactUseCase contactUseCase;

    /** Presenter yang memformat hasil ke output layar. */
    private final ContactPresenter presenter;

    public ContactView(ContactUseCase contactUseCase, ContactPresenter presenter) {
        this.contactUseCase = contactUseCase;
        this.presenter = presenter;
    }

    /** Menampilkan menu utama dan loop interaksi user. */
    public void show() {
        boolean running = true;
        while (running) {
            presenter.showContacts(contactUseCase.getAllContacts());
            printMenu();

            String input = InputUtil.input("Pilih");
            switch (input) {
                case "1" -> addContact();
                case "2" -> updateContact();
                case "3" -> searchContact();
                case "4" -> sortContact();
                case "5" -> removeContact();
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
        System.out.println("1. Tambah");
        System.out.println("2. Ubah");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    /** Form menambah kontak baru. Batal hanya jika nama diisi "x". */
    private void addContact() {
        System.out.println("[Menambah Kontak]");
        String name = InputUtil.input("Nama (x Jika Batal)");
        if (name.equals("x")) {
            return;
        }

        String phone = InputUtil.input("Telepon");
        String email = InputUtil.input("Email");

        Contact contact = contactUseCase.addContact(name, phone, email);
        presenter.showAddSuccess(contact);
    }

    /** Form mengubah kontak berdasarkan ID. Nama/Telepon/Email baru boleh dikosongkan. */
    private void updateContact() {
        System.out.println("[Mengubah Kontak]");
        String strId = InputUtil.input("ID Kontak yang diubah (x Jika Batal)");
        if (strId.equals("x")) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        String namaBaru = InputUtil.input("Nama Baru (Kosongkan jika tidak ingin mengubah)");
        String teleponBaru = InputUtil.input("Telepon Baru (Kosongkan jika tidak ingin mengubah)");
        String emailBaru = InputUtil.input("Email Baru (Kosongkan jika tidak ingin mengubah)");

        namaBaru = namaBaru.isBlank() ? null : namaBaru;
        teleponBaru = teleponBaru.isBlank() ? null : teleponBaru;
        emailBaru = emailBaru.isBlank() ? null : emailBaru;

        if (contactUseCase.updateContact(id, namaBaru, teleponBaru, emailBaru)) {
            presenter.showUpdateSuccess();
        } else {
            presenter.showUpdateFailed(id);
        }
    }

    /** Form cari kontak berdasarkan nama. */
    private void searchContact() {
        System.out.println("[Mencari Kontak]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");

        if (keyword.equals("x")) {
            return;
        }

        presenter.showSearchResults(contactUseCase.searchContacts(keyword), keyword);
    }

    /** Form mengurutkan kontak berdasarkan pilihan pengurutan. */
    private void sortContact() {
        System.out.println("[Mengurutkan Kontak]");
        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
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

        if (option < 1 || option > 2) {
            presenter.showInvalidSortChoice();
            return;
        }

        presenter.showSortedContacts(contactUseCase.sortContacts(option));
    }

    /** Form hapus kontak berdasarkan ID. */
    private void removeContact() {
        System.out.println("[Menghapus Kontak]");
        String strId = InputUtil.input("[ID Kontak] yang dihapus (x Jika Batal)");

        if (strId.equals("x")) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (contactUseCase.removeContact(id)) {
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
