package com.example.demo.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.dto.TaskRequest;
import com.example.demo.dto.TaskUpdateRequest;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.entity.Task;
import com.example.demo.service.TaskService;


@Controller
public class TaskController {


    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService ;
    @GetMapping(value = "/user/list")
    public String displayList(Model model) {
        List<Task> tasklist = taskService.searchAll();
        model.addAttribute("tasklist", tasklist);
        return "user/list";
    }


    @GetMapping(value = "/user/add")
    public String displayAdd(Model model) {
        model.addAttribute("taskRequest", new TaskRequest());

        return "user/add";
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