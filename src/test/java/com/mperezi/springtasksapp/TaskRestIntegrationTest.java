package com.mperezi.springtasksapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mperezi.springtasksapp.domain.Task;
import com.mperezi.springtasksapp.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskRestIntegrationTest {
    private static final String TASKS_API = "/api/tasks";

    @Autowired MockMvc mvc;
    @Autowired TaskService taskService;
    @Autowired ObjectMapper objectMapper;

    List<Task> tasks = Arrays.asList(
            Task.builder().title("Task 1").finished(true).build(),
            Task.builder().title("Task 2").build()
    );

    @Before
    public void setup() {
        clearTasks();
        mockTasks();
    }

    private void clearTasks() {
        taskService.getTasks().stream()
                .map(Task::getId)
                .forEach(taskService::delete);
    }

    private void mockTasks() {
        // update task list with id
        tasks = tasks.stream().map(taskService::create).collect(Collectors.toList());
    }

    @Test
    public void givenTaskList_whenGet_thenReturnOk() throws Exception {
        mvc.perform(get(TASKS_API))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value(tasks.get(0).getTitle()))
                .andExpect(jsonPath("$[0].finished").value(tasks.get(0).isFinished()))
                .andExpect(jsonPath("$[1].title").value(tasks.get(1).getTitle()));
    }

    @Test
    public void givenExistentTask_whenGet_thenReturnOk() throws Exception {
        Task task = tasks.get(0);
        mvc.perform(get(TASKS_API + "/{taskId}", task.getId()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.finished").value(task.isFinished()));
    }

    @Test
    public void givenNonExistentTask_whenGet_thenReturnNotFound() throws Exception {
        mvc.perform(get(TASKS_API + "/{taskId}", Integer.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenNewTask_whenPost_thenReturnSameTask() throws Exception {
        Task task = Task.builder().title("Task 1").build();
        mvc.perform(
                post(TASKS_API)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(task.getTitle()))
                .andExpect(jsonPath("$.finished").value(task.isFinished()));
    }

    @Test
    public void givenExistentTask_whenPatchFinish_thenReturnOk() throws Exception {
        Task task = tasks.get(1);
        mvc.perform(patch(TASKS_API + "/{taskId}/finish", task.getId()))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finished").value(true))
                .andReturn();
    }

    @Test
    public void givenNonExistentTask_whenPatchFinish_thenReturnNotFound() throws Exception {
        mvc.perform(patch(TASKS_API + "/{taskId}/finish", Integer.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenExistentTask_whenPatch_thenReturnOk() throws Exception {
        Task task = tasks.get(0);
        task.setTitle("Modified title");
        mvc.perform(
                patch(TASKS_API + "/{taskId}", task.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(task.getTitle())).andReturn();
    }

    @Test
    public void givenExistentTask_whenPatch_thenModifiesUpdateTime() throws Exception {
        Task srcTask = tasks.get(0);
        srcTask.setTitle("Modified title");
        MvcResult result = mvc.perform(
                patch(TASKS_API + "/{taskId}", srcTask.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(srcTask)))
                .andReturn();

        Task patchTask = objectMapper.readValue(result.getResponse().getContentAsString(), Task.class);
        assertThat("Updated time modified", patchTask.getUpdatedAt().isAfter(srcTask.getUpdatedAt()));
    }

    @Test
    public void givenNonExistentTask_whenPatch_thenReturnNotFound() throws Exception {
        Task task = tasks.get(0);
        mvc.perform(
                patch(TASKS_API + "/{taskId}", Integer.MAX_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenExistentTask_whenDelete_thenReturnOk() throws Exception {
        Task task = tasks.get(0);
        mvc.perform(delete(TASKS_API + "/{taskId}", task.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void givenNonExistentTask_whenDelete_thenReturnNotFound() throws Exception {
        mvc.perform(delete(TASKS_API + "/{taskId}", Integer.MAX_VALUE))
                .andExpect(status().isNotFound());
    }
}
