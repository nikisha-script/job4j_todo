package ru.job4j.todo.store;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class TaskStore {

    private final CrudRepo crud;

    public Task saveOrUpdate(Task task) {
        crud.run(session -> session.saveOrUpdate(task));
        return task;
    }

    public void delete(int id) {
        crud.run(
                "delete from Task where id = :fId",
                Map.of("fId", id)
        );
    }

    public List<Task> findAllTask() {
        return crud.query("from Task", Task.class);
    }


    public Optional<Task> findById(int id) {
        return crud.optional(
                "from Task where id = :fId", Task.class,
                Map.of("fId", id)
        );
    }

    public List<Task> findAllDone() {
        return crud.query("from Task as t where t.done = true order by created asc", Task.class);
    }

    public List<Task> findAllNew() {
        return crud.query("from Task as t where t.done = false order by created asc", Task.class);
    }

    public void updateDone(int id) {
        crud.run(
                "UPDATE Task t set t.done = true WHERE id = :fId",
                Map.of("fId", id)
        );
    }

}
