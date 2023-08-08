package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data//getter setter ....
public class StudentRequest {

    @NotBlank(message = "Name is required")
    @Size(min =3, max= 20)
    @Schema(title = "Enter student name")
    private String name;

    @NotNull(message = "age is required")
    @Max(value = 60)
    @Min(value = 15)
    @Schema(title = "Enter student age")
    private int age;
}
