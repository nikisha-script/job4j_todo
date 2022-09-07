package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class CategoryStore implements Crud {

    private final SessionFactory sf;

    public List<Category> findAll() {
        return query("from Category", Category.class, sf);
    }

    public Optional<Category> findByIdCategory(int id) {
        return optional(
                "from Category where id = :fId", Category.class,
                Map.of("fId", id),
                sf
        );
    }

}
