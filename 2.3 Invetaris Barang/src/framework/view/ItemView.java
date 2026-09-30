package framework.view;

import adapter.presenter.ItemPresenter;
import domain.entity.Item;
import framework.util.InputUtil;
import usecase.ItemUseCase;

import java.util.NoSuchElementException;

/**
 * Tampilan konsol aplikasi inventaris barang.
 */
public class ItemView {

    private final ItemUseCase itemUseCase;
    private final ItemPresenter presenter;

    public ItemView(ItemUseCase itemUseCase, ItemPresenter presenter) {
        this.itemUseCase = itemUseCase;
        this.presenter = presenter;
    }

    public void show() {
        boolean running = true;

        try {
            while (running) {
                presenter.showItems(itemUseCase.getAllItems());
                printMenu();

                String input = InputUtil.input("Pilih");

                switch (input) {
                    case "1" -> addItem();
                    case "2" -> updateStock();
                    case "3" -> searchItem();
                    case "4" -> sortItems();
                    case "5" -> removeItem();
                    case "x" -> running = false;
                    default -> presenter.showInvalidChoice();
                }

                if (running) {
                    System.out.println();
                }
            }
        } catch (NoSuchElementException e) {
            // Input habis (EOF): hentikan program dengan tenang tanpa stack trace.
        }
    }

    private void printMenu() {
        System.out.println("Menu:");
        System.out.println("1. Tambah");
        System.out.println("2. Ubah Stok");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Hapus");
        System.out.println("x. Keluar");
    }

    private void addItem() {
        System.out.println("[Menambah Barang]");

        String name = InputUtil.input("Nama (x Jika Batal)");

        if (name.equals("x")) {
            return;
        }

        String strQuantity = InputUtil.input("Jumlah");
        Integer quantity = parseQuantity(strQuantity);

        if (quantity == null) {
            return;
        }

        String category = InputUtil.input("Kategori (x Jika Batal)");

        if (category.equals("x")) {
            return;
        }

        Item item = itemUseCase.addItem(name, quantity, category);

        presenter.showAddSuccess(item);
    }

    private void updateStock() {
        System.out.println("[Mengubah Stok]");

        String strId =
                InputUtil.input("ID Barang yang diubah (x Jika Batal)");

        if (strId.equals("x")) {
            return;
        }

        Integer id = parseId(strId);

        if (id == null) {
            return;
        }

        String strQuantity =
                InputUtil.input(
                    "Jumlah Baru (Kosongkan jika tidak ingin mengubah)"
                );

        Integer quantity = null;

        if (!strQuantity.isBlank()) {
            quantity = parseQuantity(strQuantity);

            if (quantity == null) {
                return;
            }
        }

        if (itemUseCase.updateStock(id, quantity)) {
            presenter.showUpdateStockSuccess();
        } else {
            presenter.showUpdateStockFailed(id);
        }
    }

    private void searchItem() {
        System.out.println("[Mencari Barang]");

        String keyword =
                InputUtil.input("Kata Kunci (x Jika Batal)");

        if (keyword.equals("x")) {
            return;
        }

        presenter.showSearchResults(
                itemUseCase.searchItems(keyword),
                keyword
        );
    }

    private void sortItems() {
        System.out.println("[Mengurutkan Barang]");

        System.out.println("Pilihan Pengurutan:");
        System.out.println("1. Nama (A-Z)");
        System.out.println("2. Nama (Z-A)");
        System.out.println("3. Jumlah (Terkecil -> Terbesar)");
        System.out.println("4. Jumlah (Terbesar -> Terkecil)");
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

        presenter.showSortedItems(
                itemUseCase.sortItems(option)
        );
    }

    private void removeItem() {
        System.out.println("[Menghapus Barang]");

        String strId =
                InputUtil.input(
                    "[ID Barang] yang dihapus (x Jika Batal)"
                );

        if (strId.equals("x")) {
            return;
        }

        Integer id = parseId(strId);

        if (id == null) {
            return;
        }

        if (itemUseCase.removeItem(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    private Integer parseId(String strId) {
        try {
            return Integer.parseInt(strId.trim());
        } catch (NumberFormatException e) {
            presenter.showInvalidId();
            return null;
        }
    }

    private Integer parseQuantity(String strQuantity) {
        try {
            return Integer.parseInt(strQuantity.trim());
        } catch (NumberFormatException e) {
            presenter.showInvalidQuantity();
            return null;
        }
    }
}