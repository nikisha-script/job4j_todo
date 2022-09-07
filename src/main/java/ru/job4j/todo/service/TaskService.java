package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.CategoryStore;
import ru.job4j.todo.store.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskStore store;
    private final CategoryStore categoryStore;

    public TaskService(TaskStore store, CategoryStore categoryStore) {
        this.store = store;
        this.categoryStore = categoryStore;
    }

    public Task saveOrUpdate(Task task) {
        return store.saveOrUpdate(task);
    }

    public void delete(int id) {
        store.delete(id);
    }

    public List<Task> findAllTask() {
        return store.findAllTask();
    }


    public Optional<Task> findById(int id) {
        return store.findById(id);
    }

    public List<Task> findAllDone() {
        return store.findAllDone();
    }

    public List<Task> findAllNew() {
        return store.findAllNew();
    }

    public void updateDone(int id) {
        store.updateDone(id);
    }

    public List<Category> findAll() {
        return categoryStore.findAll();
    }

    public Optional<Category> findByIdCategory(int id) {
        return categoryStore.findByIdCategory(id);
    }

    public List findAllTaskAndCategories() {
        return store.findAllTaskAndCategories();
    }



}
