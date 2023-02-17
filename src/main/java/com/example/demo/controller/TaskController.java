package com.example.demo.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;

import javax.print.DocFlavor;


@Controller
public class TaskController {


    @Autowired
    private TaskService taskService;



    @GetMapping(value = "/user/list")
    public String displayList(Model model) {

        String RoleCond1 ="[ROLE_USER]";
        String RoleCond2 ="[ROLE_ADMIN]";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String Role = authentication.getAuthorities().toString();
        System.out.println("role="+Role);
        if (Role.equals(RoleCond2)) {
            System.out.println("ADMIN CHECKED");
            List<Task> tasklist = taskService.searchAll();
            System.out.println("LISTING TABLE DATA");
            model.addAttribute("tasklist", tasklist);
            System.out.println("MODELLING");

            return "user/list";

        } else if (Role.equals(RoleCond1)) {
            System.out.println("USER CHECKED");
            List<Task> tasklist = taskService.findByRole();
            System.out.println("LISTING TABLE CHECKED");
            model.addAttribute("tasklist", tasklist);
            System.out.println("MODEL CHECKED");
            return "user/list";
        }else {
            System.out.println("FAILED");
            return "user/list";
        }

    }


    @GetMapping(value = "/user/add")
    public String displayAdd(Model model) {
        model.addAttribute("taskRequest", new TaskRequest());

        return "user/add";
    }
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(User name) {
        return name.getName();
    }


    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
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
        taskUpdateRequest.setDeleteDate(String.valueOf(task.getDeleteDate()));
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