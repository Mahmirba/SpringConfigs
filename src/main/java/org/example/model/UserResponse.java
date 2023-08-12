package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//getter setter ....
@AllArgsConstructor // constructor with all params in order
@NoArgsConstructor
public class UserResponse {


    @Schema(title = "unique key of the student")
    private Long id;

    @Schema(title = "get user name")
    private String name;
}
