package com.example.demo.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskUpdateRequest;
import com.example.demo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.entity.Task;


@Service
@Transactional(rollbackFor = Exception.class)
public class TaskService {



    @Autowired
    private TaskRepository taskRepository;

    public List<Task> searchAll() {
        return taskRepository.findAll();
    }
    public List<Task> findByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }

        return  taskRepository.findByUsername(currentUserName);
    }




    /**
     * DBから取得したユーザ情報をSpring Bootのユーザ情報に変更する。
     *
     * @param user DBから取得したユーザ情報
     * @return Spring Bootのユーザ情報
     */



    public void create(TaskRequest taskRequest) {
        Date now = new Date();
        Task task = new Task();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }

        String Current = String.valueOf(currentUserName);
        task.setUsername(Current);
        task.setTask(taskRequest.getTask());
        task.setContents(taskRequest.getContents());
        LocalDate Ddate = LocalDate.parse(taskRequest.getDeadline());
        Date DeldateUtil = java.sql.Date.valueOf(Ddate);
        task.setDeadline(DeldateUtil);
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
        LocalDate Ddate = LocalDate.parse(taskUpdateRequest.getDeadline());
        Date DelDateUtil = java.sql.Date.valueOf(Ddate);
        task.setDeadline(DelDateUtil);
        task.setTask(taskUpdateRequest.getTask());
        task.setUpdateDate(new Date());
        taskRepository.save(task);
    }

}