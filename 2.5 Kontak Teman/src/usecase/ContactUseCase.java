package usecase;

import domain.entity.Contact;
import domain.repository.IContactRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Use case yang menangani logika bisnis aplikasi kontak teman.
 * Tidak melakukan I/O — hanya memproses data dan mengembalikan hasil
 * ke layer presenter/view.
 */
public class ContactUseCase {
    /** Port repository yang di-inject dari luar (Dependency Inversion). */
    private final IContactRepository contactRepository;

    public ContactUseCase(IContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /** Mengambil semua kontak yang terdaftar. */
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    /** Menambahkan kontak baru dan mengembalikan entity yang tersimpan. */
    public Contact addContact(String name, String phone, String email) {
        return contactRepository.save(name, phone, email);
    }

    /**
     * Mengubah data kontak berdasarkan ID.
     * Parameter bernilai null berarti field tersebut tidak diubah.
     *
     * @return true jika kontak ditemukan (dan field yang tidak null diperbarui)
     */
    public boolean updateContact(int id, String name, String phone, String email) {
        return contactRepository.update(id, name, phone, email);
    }

    /** Menghapus kontak berdasarkan ID. */
    public boolean removeContact(int id) {
        return contactRepository.deleteById(id);
    }

    /** Mencari kontak yang namanya mengandung kata kunci (case-insensitive). */
    public List<Contact> searchContacts(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return contactRepository.findAll().stream()
                .filter(contact -> contact.getName().toLowerCase().contains(lowerKeyword))
                .toList();
    }

    /**
     * Mengembalikan salinan daftar kontak yang sudah diurutkan, tanpa mengubah
     * urutan data asli di penyimpanan.
     *
     * @param option 1 = Nama (A-Z), 2 = Nama (Z-A)
     */
    public List<Contact> sortContacts(int option) {
        List<Contact> items = new ArrayList<>(contactRepository.findAll());

        Comparator<Contact> comparator = switch (option) {
            case 1 -> Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER);
            case 2 -> Comparator.comparing(Contact::getName, String.CASE_INSENSITIVE_ORDER).reversed();
            default -> null;
        };

        if (comparator != null) {
            items.sort(comparator);
        }
        return items;
    }
}
