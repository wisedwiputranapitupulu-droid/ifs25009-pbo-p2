package adapter.repository;

import domain.entity.Contact;
import domain.repository.IContactRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementasi repository menggunakan penyimpanan in-memory berbasis {@link List}.
 * Berada di layer adapter — mengimplementasikan port dari domain sekaligus
 * menyembunyikan detail struktur data dari layer di atasnya.
 */
public class ContactRepository implements IContactRepository {
    /** Penyimpanan data kontak di memori. */
    private final List<Contact> data = new ArrayList<>();

    /** Penghitung ID otomatis, bertambah setiap kali kontak baru disimpan. */
    private int idCounter = 0;

    @Override
    public List<Contact> findAll() {
        // Salinan defensif agar data internal tidak bisa diubah dari luar
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Contact> findById(int id) {
        return data.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst();
    }

    @Override
    public Contact save(String name, String phone, String email) {
        Contact contact = new Contact(nextId(), name, phone, email);
        data.add(contact);
        return contact;
    }

    @Override
    public boolean update(int id, String name, String phone, String email) {
        Optional<Contact> contact = findById(id);
        if (contact.isEmpty()) {
            return false;
        }

        Contact c = contact.get();
        if (name != null) {
            c.setName(name);
        }
        if (phone != null) {
            c.setPhone(phone);
        }
        if (email != null) {
            c.setEmail(email);
        }
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(contact -> contact.getId() == id);
    }

    /** Menghasilkan ID unik berikutnya. */
    private int nextId() {
        return ++idCounter;
    }
}
