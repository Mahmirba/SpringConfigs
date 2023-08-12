package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.business.StudentService;
import org.example.business.UserService;
import org.example.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @Operation(description = "create new user")
    @ApiResponses(value = {@ApiResponse(description = "successful", responseCode = "201"),
            @ApiResponse(description = "Bad Request", responseCode = "400")})
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) throws Exception {

        return ResponseEntity.ok(userService.create(request));
    }

}
