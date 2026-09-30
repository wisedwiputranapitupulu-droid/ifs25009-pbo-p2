package usecase;

import domain.entity.Item;
import domain.repository.IItemRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ItemUseCase {

    private final IItemRepository itemRepository;

    public ItemUseCase(
            IItemRepository itemRepository
    ) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item addItem(
            String name,
            int quantity,
            String category
    ) {
        return itemRepository.save(
                name,
                quantity,
                category
        );
    }

    public boolean updateStock(
            int id,
            Integer quantity
    ) {
        Optional<Item> item =
                itemRepository.findById(id);

        if (item.isEmpty()) {
            return false;
        }

        if (quantity != null) {
            itemRepository.updateQuantity(
                    id,
                    quantity
            );
        }

        return true;
    }

    public boolean removeItem(int id) {
        return itemRepository.deleteById(id);
    }

    public List<Item> searchItems(
            String keyword
    ) {
        String lowerKeyword =
                keyword.toLowerCase();

        return itemRepository.findAll()
                .stream()
                .filter(
                    item ->
                        item.getName()
                            .toLowerCase()
                            .contains(lowerKeyword)
                )
                .toList();
    }

    public List<Item> sortItems(
            int option
    ) {
        List<Item> items =
                new ArrayList<>(
                        itemRepository.findAll()
                );

        Comparator<Item> comparator =
                switch (option) {

                    case 1 ->
                        Comparator.comparing(
                            Item::getName,
                            String.CASE_INSENSITIVE_ORDER
                        );

                    case 2 ->
                        Comparator.comparing(
                            Item::getName,
                            String.CASE_INSENSITIVE_ORDER
                        ).reversed();

                    case 3 ->
                        Comparator.comparingInt(
                            Item::getQuantity
                        );

                    case 4 ->
                        Comparator.comparingInt(
                            Item::getQuantity
                        ).reversed();

                    default -> null;
                };

        if (comparator != null) {
            items.sort(comparator);
        }

        return items;
    }
}