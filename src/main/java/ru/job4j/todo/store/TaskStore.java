package ru.job4j.todo.store;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class TaskStore implements Crud {

    private final SessionFactory sf;

    public Task saveOrUpdate(Task task) {
        run(session -> session.saveOrUpdate(task), sf);
        return task;
    }

    public void delete(int id) {
        run(
                "delete from Task where id = :fId",
                Map.of("fId", id),
                sf
        );

    }

    public List<Task> findAllTask() {
        return query("from Task", Task.class, sf);
    }


    public Optional<Task> findById(int id) {
        return optional(
                "from Task where id = :fId", Task.class,
                Map.of("fId", id),
                sf
        );
    }

    public List<Task> findAllDone() {
        return query("from Task as t where t.done = true order by created asc", Task.class, sf);
    }

    public List<Task> findAllNew() {
        return query("from Task as t where t.done = false order by created asc", Task.class, sf);
    }

    public void updateDone(int id) {
        run(
                "UPDATE Task t set t.done = true WHERE id = :fId",
                Map.of("fId", id),
                sf
        );
    }

}
