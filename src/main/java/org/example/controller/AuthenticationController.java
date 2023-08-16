package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.business.AuthenticationService;
import org.example.business.UserService;
import org.example.model.LoginRequest;
import org.example.model.LoginResponse;
import org.example.model.UserRequest;
import org.example.model.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @Operation(description = "User Login")
    @ApiResponses(value = {@ApiResponse(description = "successful", responseCode = "200"),
            @ApiResponse(description = "Bad Request", responseCode = "400")})
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) throws Exception {

        return ResponseEntity.ok(authenticationService.login(request));
    }

}
