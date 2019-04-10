package com.mperezi.springtasksapp.repository;

import com.mperezi.springtasksapp.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
