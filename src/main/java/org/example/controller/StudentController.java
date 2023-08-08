package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.business.StudentService;
import org.example.exceptions.BadRequestException;
import org.example.model.RequestDto;
import org.example.model.ResponseDto;
import org.example.model.StudentRequest;
import org.example.model.StudentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.Name;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/hello")
    public String print() {
        return "Hello";
    }

    @Operation(description = "create new student")
    @ApiResponses(value = {@ApiResponse(description = "successful", responseCode = "201"),
            @ApiResponse(description = "Bad Request", responseCode = "400")})
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StudentResponse> create(@RequestBody @Valid StudentRequest request) throws Exception {

        return ResponseEntity.ok(studentService.create(request));
    }

    @Operation(description = "get new student")
    @ApiResponses(value = {@ApiResponse(description = "successful", responseCode = "200"),
            @ApiResponse(description = "Bad Request", responseCode = "400")})
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<StudentResponse> getStudent(@PathVariable @Valid Long id) throws Exception {

        return ResponseEntity.ok(studentService.find(id));
    }

    @PostMapping("/create2")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> createTimeout(@RequestBody RequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(new ResponseDto());
    }


}
