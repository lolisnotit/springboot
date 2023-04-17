package com.example.demo.dto;

import java.io.Serializable;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;


@Data
public class TaskRequest implements Serializable {
    @NotEmpty(message = "ENTER THE NAME")
    @Size(max = 100, message = "MAX NAME ")
    private String task;

    @Size(max = 255, message = "MAX TASK CONTENTS")
    private String contents;

    @Size(min=10, max =10 , message = "ENTER DEADLINE")
    private String deadline;



}