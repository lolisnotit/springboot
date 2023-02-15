package com.example.demo.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskUpdateRequest;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Task;
import com.example.demo.repository.TaskRepository;


@Service
@Transactional(rollbackFor = Exception.class)
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


    public List<Task> searchAll() {
        return taskRepository.findAll();
    }

    public void create(TaskRequest taskRequest) {
        Date now = new Date();
        Task task = new Task();
        task.setUsername("UsernameHolder");
        task.setTask(taskRequest.getTask());
        task.setContents(taskRequest.getContents());
        LocalDate Ddate = LocalDate.parse(taskRequest.getDeleteDate());
        Date DeldateUtil = java.sql.Date.valueOf(Ddate);
        task.setDeleteDate(DeldateUtil);
        task.setCreateDate(now);
        task.setUpdateDate(now);
        taskRepository.save(task);
    }
    public Task findById(Long id) {
        return taskRepository.findById(id).get();
    }
    public void delete(Long id) {
        Task TASK = findById(id);
        taskRepository.delete(TASK);
    }
    public void update(TaskUpdateRequest taskUpdateRequest) {
        Task task = findById(taskUpdateRequest.getId());
        task.setContents(taskUpdateRequest.getContents());
        LocalDate Ddate = LocalDate.parse(taskUpdateRequest.getDeleteDate());
        Date DeldateUtil = java.sql.Date.valueOf(Ddate);
        task.setDeleteDate(DeldateUtil);
        task.setTask(taskUpdateRequest.getTask());
        task.setUpdateDate(new Date());
        taskRepository.save(task);
    }

}