package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@ThreadSafe
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    private void getSession(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("user");
        }
        model.addAttribute("user", user);
    }

    @GetMapping("/tasks")
    public String tasks(Model model, HttpSession session) {
        getSession(model, session);
        model.addAttribute("tasks", service.findAllTaskAndCategories());
        return "tasks";
    }

    @GetMapping("/addTask")
    public String addTask(Model model, HttpSession session) {
        getSession(model, session);
        model.addAttribute("categories", service.findAll());
        return "/addTask";
    }

    @PostMapping("/createTask")
    public String createTask(@RequestParam("category_id") List<Integer> categoryId,
                             @ModelAttribute Task task,
                             HttpSession session) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        task.setCreated(LocalDateTime.now());
        for (Integer id : categoryId) {
            var category = service.findByIdCategory(id).get();
            task.getCategories().add(category);
        }
        service.saveOrUpdate(task);
        return "redirect:/tasks";
    }

    @GetMapping("/newTask")
    public String newTask(Model model, HttpSession session) {
        getSession(model, session);
        model.addAttribute("newTask", service.findAllNew());
        return "newTasks";
    }

    @GetMapping("/doneTasks")
    public String doneTask(Model model, HttpSession session) {
        getSession(model, session);
        model.addAttribute("doneTask", service.findAllDone());
        return "doneTasks";
    }

    @GetMapping("/tasks/{id}")
    public String getTask(Model model, HttpSession session, @PathVariable("id") int id) {
        getSession(model, session);
        model.addAttribute("t", service.findById(id).get());
        return "task";
    }

    @GetMapping("/update/{id}")
    public String updateTask(Model model, HttpSession session, @PathVariable("id") int id) {
        getSession(model, session);
        model.addAttribute("t", service.findById(id).get());
        return "update";
    }

    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        service.delete(id);
        return "redirect:/tasks";
    }

    @PostMapping("/isDone/{id}")
    public String doneTask(@PathVariable("id") int id) {
        service.updateDone(id);
        return "redirect:/tasks";
    }

    @PostMapping("update")
    public String update(@ModelAttribute Task task, HttpSession session) {
        User user = (User) session.getAttribute("user");
        task.setUser(user);
        service.saveOrUpdate(task);
        return "redirect:/tasks";
    }


}
