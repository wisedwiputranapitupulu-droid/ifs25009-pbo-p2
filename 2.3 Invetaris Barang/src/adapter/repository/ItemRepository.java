package adapter.repository;

import domain.entity.Item;
import domain.repository.IItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemRepository implements IItemRepository {

    private final List<Item> data = new ArrayList<>();

    private int idCounter = 0;

    @Override
    public List<Item> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public Optional<Item> findById(int id) {
        return data.stream()
                .filter(item -> item.getId() == id)
                .findFirst();
    }

    @Override
    public Item save(
            String name,
            int quantity,
            String category
    ) {
        Item item =
                new Item(
                        nextId(),
                        name,
                        quantity,
                        category
                );

        data.add(item);

        return item;
    }

    @Override
    public boolean updateQuantity(
            int id,
            int quantity
    ) {
        Optional<Item> item = findById(id);

        if (item.isEmpty()) {
            return false;
        }

        item.get().setQuantity(quantity);

        return true;
    }

    @Override
    public boolean deleteById(int id) {
        return data.removeIf(
                item -> item.getId() == id
        );
    }

    private int nextId() {
        return ++idCounter;
    }
}