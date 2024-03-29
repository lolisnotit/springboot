package com.example.demo.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskUpdateRequest;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;


@Controller
public class TaskController {


    @Autowired
    private TaskService taskService;


//    public boolean checkAnon(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String Role = authentication.getAuthorities().toString();
//        String RoleAnon ="[ROLE_ANONYMOUS]";
//        if (Role.equals(RoleAnon)) {
//            System.out.println("role="+Role);
//            return true;
//        }
//        return false;
//
//    }
    @GetMapping(value = "/user/list")
    public String displayList(Model model) {

        String RoleCond1 ="[ROLE_USER]";
        String RoleCond2 ="[ROLE_ADMIN]";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Role = authentication.getAuthorities().toString();
        System.out.println("role="+Role);
        if (Role.equals(RoleCond2)) {

            List<Task> tasklist = taskService.searchAll();

            model.addAttribute("tasklist", tasklist);


            return "user/list";

        } else if (Role.equals(RoleCond1)) {

            List<Task> tasklist = taskService.findByUserName();

            model.addAttribute("tasklist", tasklist);

            return "user/list";
        }else {
            System.out.println("FAILED");
            return "Role check failed";
        }

    }


    @GetMapping(value = "/user/add")
    public String displayAdd(Model model) {
        model.addAttribute("taskRequest", new TaskRequest());
        return "user/add";
    }

    @PostMapping(value = "/user/create")
    public String create(@Validated @ModelAttribute TaskRequest taskRequest, BindingResult result, Model model) throws ParseException {

        if (result.hasErrors()) {

            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/add";
        }

        taskService.create(taskRequest);
        return "redirect:/user/list";
    }


    @GetMapping("/user/{id}")
    public String displayView(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        if (task == null) {
            return "error";
        }
        model.addAttribute("taskData", task);
        return "user/view";
    }

    @GetMapping("/user/{id}/delete")
    public String delete(@PathVariable Long id, Model model) {

        taskService.delete(id);
        return "redirect:/user/list";
    }

    @GetMapping("/user/{id}/edit")
    public String displayEdit(@PathVariable Long id, Model model) {
        Task task = taskService.findById(id);
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        taskUpdateRequest.setId(task.getId());
        taskUpdateRequest.setTask(task.getTask());
        taskUpdateRequest.setContents(task.getContents());
        taskUpdateRequest.setDeadline(String.valueOf(task.getDeadline()));
        model.addAttribute("taskUpdateRequest", taskUpdateRequest);
        return "user/edit";
    }
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String update(@Validated @ModelAttribute TaskUpdateRequest taskUpdateRequest, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<String> errorList = new ArrayList<String>();
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            model.addAttribute("validationError", errorList);
            return "user/edit";
        }
        taskService.update(taskUpdateRequest);
        return String.format("redirect:/user/%d", taskUpdateRequest.getId());
    }




}