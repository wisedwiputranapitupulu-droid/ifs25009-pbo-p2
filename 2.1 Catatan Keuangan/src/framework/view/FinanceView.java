package framework.view;

import adapter.presenter.FinancePresenter;
import domain.entity.SortOption;
import domain.entity.Transaction;
import domain.entity.TransactionType;
import framework.util.InputUtil;
import usecase.FinanceUseCase;

/**
 * Tampilan konsol aplikasi catatan keuangan.
 * Menerima input user, memanggil use case, dan menampilkan hasil via presenter.
 * Layer ini tidak mengandung logika bisnis — hanya menangani interaksi user.
 */
public class FinanceView {
    /** Use case yang menjalankan operasi bisnis. */
    private final FinanceUseCase financeUseCase;

    /** Presenter yang memformat hasil ke output layar. */
    private final FinancePresenter presenter;

    public FinanceView(FinanceUseCase financeUseCase, FinancePresenter presenter) {
        this.financeUseCase = financeUseCase;
        this.presenter = presenter;
    }

    /** Menampilkan menu utama dan loop interaksi user. */
    public void show() {
        boolean running = true;
        while (running) {
            // Tampilkan daftar transaksi dan saldo terkini sebelum menu
            presenter.showTransactions(financeUseCase.getAllTransactions(), financeUseCase.getBalance());
            printMenu();

            String input = InputUtil.input("Pilih");
            switch (input) {
                case "1" -> addTransaction(TransactionType.INCOME, "Pemasukan");
                case "2" -> addTransaction(TransactionType.EXPENSE, "Pengeluaran");
                case "3" -> searchTransaction();
                case "4" -> sortTransaction();
                case "5" -> viewBalance();
                case "6" -> removeTransaction();
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
        System.out.println("1. Tambah Pemasukan");
        System.out.println("2. Tambah Pengeluaran");
        System.out.println("3. Cari");
        System.out.println("4. Urutkan");
        System.out.println("5. Lihat Saldo");
        System.out.println("6. Hapus");
        System.out.println("x. Keluar");
    }

    /** Form tambah transaksi baru (pemasukan/pengeluaran). */
    private void addTransaction(TransactionType type, String label) {
        System.out.println("[Tambah " + label + "]");
        String description = InputUtil.input("Keterangan (x Jika Batal)");

        if (description.equals("x")) {
            return;
        }

        String strAmount = InputUtil.input("Jumlah");
        Integer amount = parseAmount(strAmount);
        if (amount == null) {
            presenter.showInvalidAmount();
            return;
        }

        Transaction transaction = financeUseCase.addTransaction(description, amount, type);
        presenter.showAddSuccess(transaction);
    }

    /** Form cari transaksi berdasarkan keterangan. */
    private void searchTransaction() {
        System.out.println("[Cari Transaksi]");
        String keyword = InputUtil.input("Kata Kunci (x Jika Batal)");

        if (keyword.equals("x")) {
            return;
        }

        presenter.showSearchResults(financeUseCase.searchTransactions(keyword), keyword);
    }

    /** Form urutkan transaksi berdasarkan kriteria yang dipilih. */
    private void sortTransaction() {
        System.out.println("[Urutkan Transaksi]");
        System.out.println("1. Jumlah (Terkecil)");
        System.out.println("2. Jumlah (Terbesar)");
        System.out.println("3. Pemasukan Dulu");
        System.out.println("4. Pengeluaran Dulu");
        System.out.println("x. Batal");

        String strChoice = InputUtil.input("Pilih");
        if (strChoice.equals("x")) {
            return;
        }

        SortOption option = SortOption.fromMenuChoice(strChoice);
        if (option == null) {
            presenter.showInvalidSortOption();
            return;
        }

        presenter.showSortedTransactions(financeUseCase.sortTransactions(option));
    }

    /** Menampilkan saldo saat ini. */
    private void viewBalance() {
        presenter.showBalance(financeUseCase.getBalance());
    }

    /** Form hapus transaksi berdasarkan ID. */
    private void removeTransaction() {
        System.out.println("[Hapus Transaksi]");
        String strId = InputUtil.input("ID Transaksi (x Jika Batal)");

        if (strId.equals("x")) {
            return;
        }

        Integer id = parseId(strId);
        if (id == null) {
            return;
        }

        if (financeUseCase.removeTransaction(id)) {
            presenter.showRemoveSuccess();
        } else {
            presenter.showRemoveFailed(id);
        }
    }

    /** Mem-parsing jumlah dari string. Mengembalikan null jika tidak valid (bukan angka atau &lt;= 0). */
    private Integer parseAmount(String strAmount) {
        try {
            int amount = Integer.parseInt(strAmount.trim());
            return amount > 0 ? amount : null;
        } catch (NumberFormatException e) {
            return null;
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
