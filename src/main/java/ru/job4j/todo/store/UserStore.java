package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Log4j2
@Repository
public class UserStore implements Crud {

    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        run(session -> session.saveOrUpdate(user), sf);
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        run(session -> session.merge(user), sf);
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        run(
                "delete from User where id = :fId",
                Map.of("fId", userId),
                sf
        );
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        return query("from User", User.class, sf);
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        return optional(
                "from User where id = :fId", User.class,
                Map.of("fId", id),
                sf
        );
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        return query(
                "from User where login like :fKey", User.class,
                Map.of("fKey", "%" + key + "%"),
                sf
        );
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        return optional(
                "from User where login = :fLogin", User.class,
                Map.of("fLogin", login),
                sf
        );
    }

    public Optional<User> findUserByEmailAndPwd(User user) {
        return optional(
                "from User as u where u.login = :fLogin and u.password = :fPassword", User.class,
                Map.of("fLogin", user.getLogin(), "fPassword", user.getPassword()),
                sf
        );
    }

}
