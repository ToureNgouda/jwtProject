package com.dev.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{

}
