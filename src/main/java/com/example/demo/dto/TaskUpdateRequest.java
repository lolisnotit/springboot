package com.example.demo.dto;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaskUpdateRequest extends TaskRequest implements Serializable {

    @NotNull
    private Long id;



}