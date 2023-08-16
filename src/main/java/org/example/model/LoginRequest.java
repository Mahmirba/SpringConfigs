package org.example.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data//getter setter ....
public class LoginRequest {

    @NotBlank(message = "username is required")
    @Size(min =3, max= 20)
    @Schema(title = "Enter username")
    private String username;

    @NotBlank
    @Size(min =4)
    private String password;


}
