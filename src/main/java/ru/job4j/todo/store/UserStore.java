package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Log4j2
@Repository
public class UserStore {

    private final SessionFactory sf;

    /**
     * Сохранить в базе.
     * @param user пользователь.
     * @return пользователь с id.
     */
    public User create(User user) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at create method", e);
        }
        return user;
    }

    /**
     * Обновить в базе пользователя.
     * @param user пользователь.
     */
    public void update(User user) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at update method", e);
        }
    }

    /**
     * Удалить пользователя по id.
     * @param userId ID
     */
    public void delete(int userId) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery(
                            "DELETE User WHERE id = :fId")
                    .setParameter("fId", userId)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at delete method", e);
        }
    }

    /**
     * Список пользователь отсортированных по id.
     * @return список пользователей.
     */
    public List<User> findAllOrderById() {
        List rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from User order by id");
            rsl = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at findAllOrderById method", e);
        }
        return rsl;
    }

    /**
     * Найти пользователя по ID
     * @return пользователь.
     */
    public Optional<User> findById(int id) {
        Optional<User> rsl = Optional.empty();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "from User as u where u.id = :fId", User.class);
            query.setParameter("fId", id);
            rsl = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at findById method", e);
        }
        return rsl;
    }

    /**
     * Список пользователей по login LIKE %key%
     * @param key key
     * @return список пользователей.
     */
    public List<User> findByLikeLogin(String key) {
        List rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery(
                    "from User where login like :fkey");
            query.setParameter("fkey", key);
            rsl = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at findAllOrderById method", e);
        }
        return rsl;
    }

    /**
     * Найти пользователя по login.
     * @param login login.
     * @return Optional or user.
     */
    public Optional<User> findByLogin(String login) {
        Optional<User> rsl = Optional.empty();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "from User as u where u.login = :flogin", User.class);
            query.setParameter("flogin", login);
            rsl = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at findByLogin method", e);
        }
        return rsl;
    }

    public Optional<User> findUserByEmailAndPwd(User user) {
        Optional<User> rsl = Optional.empty();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "from User as u where u.login = :fLogin and u.password = :fPassword", User.class);
            query.setParameter("fLogin", user.getLogin());
            query.setParameter("fPassword", user.getPassword());
            rsl = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at findById method", e);
        }
        return rsl;
    }

}
