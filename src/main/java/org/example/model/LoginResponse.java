package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data//getter setter ....
@AllArgsConstructor // constructor with all params in order
@NoArgsConstructor
public class LoginResponse {


    @Schema(title = "generated token")
    private String token;
}
