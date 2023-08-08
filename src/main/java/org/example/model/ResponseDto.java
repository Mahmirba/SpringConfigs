package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//getter setter ....
@AllArgsConstructor // constructor with all params in order
@NoArgsConstructor
public class ResponseDto {

    @Schema(title = "get student name")
    private String name;

    @Schema(title = "get student age")
    private int age;
}
