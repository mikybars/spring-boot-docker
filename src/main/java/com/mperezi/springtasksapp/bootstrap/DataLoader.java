package com.mperezi.springtasksapp.bootstrap;

import com.mperezi.springtasksapp.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataLoader implements CommandLineRunner {
    private final TaskService taskService;

    public DataLoader(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (taskService.getTasks().isEmpty()) {
            log.debug("Tasks DB is empty. Populating it with some mock objects.");
            taskService.mock();
        }
    }
}

