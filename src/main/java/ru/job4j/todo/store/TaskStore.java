package ru.job4j.todo.store;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class TaskStore {

    private final SessionFactory sf;

    public Task saveOrUpdate(Task task) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception at saveOrUpdate method", e);
        }
        return task;
    }

    public void delete(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at delete method", e);
        }
    }

    public List<Task> findAllTask() {
        List rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Task");
            rsl = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at findAllTask method", e);
        }
        return rsl;
    }


    public Optional<Task> findById(int id) {
        Optional<Task> rsl = Optional.empty();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                    "from Task as u where u.id = :fId", Task.class);
            query.setParameter("fId", id);
            rsl = query.uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at findById method", e);
        }
        return rsl;
    }

    public List<Task> findAllDone() {
        List rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Task as t where t.done = true order by created asc");
            rsl = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at findAllDone method", e);
        }
        return rsl;
    }

    public List<Task> findAllNew() {
        List rsl = new ArrayList<>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Task as t where t.done = false order by created asc");
            rsl = query.getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at findAllNew method", e);
        }
        return rsl;
    }

    public void updateDone(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Task t set t.done = true WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("SQLException at updateDone method", e);
        }
    }

}
