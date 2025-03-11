package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskMapper taskMapper;
    // BEGIN
    @GetMapping
    public List<TaskDTO> getAll() {
        return taskRepository.findAll().stream().map(task -> taskMapper.map(task)).toList();
    }
    @GetMapping(path = "/{id}")
    public TaskDTO get(@PathVariable long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        return taskMapper.map(task);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO post(@RequestBody TaskCreateDTO dto) {
        return taskMapper.map(taskRepository.save(taskMapper.map(dto)));
    }
    @PutMapping(path = "/{id}")
    public TaskDTO put(@RequestBody TaskUpdateDTO dto, @PathVariable long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("not found"));
        taskMapper.update(dto, task);
        return taskMapper.map(taskRepository.save(task));
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        taskRepository.deleteById(id);
    }
    // END
}
