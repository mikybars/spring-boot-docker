package com.mperezi.springtasksapp.web.rest;

import com.mperezi.springtasksapp.domain.Task;
import com.mperezi.springtasksapp.service.TaskService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "task")
public class TaskRest {
    private final TaskService taskService;

    public TaskRest(TaskService taskService) {
        this.taskService = taskService;
    }

    @ApiOperation("Retrieve list of tasks")
    @GetMapping(value = "/tasks")
    public ResponseEntity<List<Task>> getTasks() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasks());
    }

    @ApiOperation("Find task by ID")
    @ApiResponses(
            @ApiResponse(code = 404, message = "Task not found")
    )
    @GetMapping(value = "/tasks/{taskId}")
    public ResponseEntity<Task> getTask(@ApiParam("ID of task to return") @PathVariable Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTask(taskId));
    }

    @ApiOperation("Submit a new task")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(
            @ApiResponse(code = 201, message = "Task created")
    )
    @PostMapping(value = "/tasks")
    public ResponseEntity<Task> postTask(@ApiParam("Task object to be submitted") @RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(task));
    }

    @ApiOperation("Updates an existing task")
    @ApiResponses(
            @ApiResponse(code = 404, message = "Task not found")
    )
    @PatchMapping(value = "/tasks/{taskId}")
    public ResponseEntity<Task> patchTask(
            @ApiParam("ID of task to be updated") @PathVariable Long taskId,
            @ApiParam("Task object with new data") @RequestBody Task task) {
        task.setId(taskId);
        return ResponseEntity.status(HttpStatus.OK).body(taskService.update(task));
    }

    @ApiOperation("Mark task as finished")
    @ApiResponses(
            @ApiResponse(code = 404, message = "Task not found")
    )
    @PatchMapping(value = "/tasks/{taskId}/finish")
    public ResponseEntity<Task> finishTask(@ApiParam("ID of task to be updated") @PathVariable Long taskId) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.finish(taskId));
    }

    @ApiOperation("Delete task by ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successfully deleted task"),
            @ApiResponse(code = 404, message = "Task not found")
    })
    @DeleteMapping(value = "/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(@ApiParam("ID of task to be deleted") @PathVariable Long taskId) {
        taskService.delete(taskId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
