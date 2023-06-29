package ru.javarush.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javarush.todo.dto.TaskDto;
import ru.javarush.todo.entity.Task;
import ru.javarush.todo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {
    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String tasks(Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        List<Task> tasks = taskService.findAll((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);

        int pagesTotal = (int) Math.ceil(1.0 * taskService.findAllCount() / limit);
        if (pagesTotal > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pagesTotal).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "tasks";
    }

    @PostMapping("/{id}")
    public String edit(Model model,
                       @PathVariable Integer id,
                       @RequestBody TaskDto taskDto) {
        if (isNull(id) || id <= 0) {
            logger.error("An incorrect task ID: " + id + " was sent.");
            throw new RuntimeException("Wrong ID.");
        }

        Task task = taskService.edit(id, taskDto.getDescription(), taskDto.getStatus());
        return tasks(model, 1, 10);
    }

    @PostMapping("/")
    public String add(Model model,
                      @RequestBody TaskDto taskDto) {
        Task task = taskService.create(taskDto.getDescription(), taskDto.getStatus());
        return tasks(model, 1, 10);
    }

    @DeleteMapping("/{id}")
    public String delete(Model model,
                         @PathVariable Integer id) {
        if (isNull(id) || id <= 0) {
            logger.error("An incorrect task ID: " + id + " was sent.");
            throw new RuntimeException("Wrong ID.");
        }

        taskService.delete(id);
        return tasks(model, 1, 10);
    }
}
