package domain.repository;

import domain.entity.Item;

import java.util.List;
import java.util.Optional;

public interface IItemRepository {

    List<Item> findAll();

    Optional<Item> findById(int id);

    Item save(
            String name,
            int quantity,
            String category
    );

    boolean updateQuantity(
            int id,
            int quantity
    );

    boolean deleteById(int id);
}