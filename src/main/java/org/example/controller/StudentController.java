package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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

    @Operation(description = "delete student")
    @ApiResponses(value = {@ApiResponse(description = "successful", responseCode = "200"),
            @ApiResponse(description = "Bad Request", responseCode = "400")})
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Valid Long id) throws Exception {

         studentService.delete(id);
    }

    @Operation(description = "search on students")
    @ApiResponses(value = {@ApiResponse(description = "successful", responseCode = "200"),
            @ApiResponse(description = "Bad Request", responseCode = "400")})
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<StudentResponse>> search(@RequestParam(name = "id", required = false) Long id,
                                                        @RequestParam(name = "name", required = false) String name,
                                                        @RequestParam(name = "age", required = false) Integer age,
                                                        @RequestParam(name = "page", required = false) Integer page,
                                                        @RequestParam(name = "pageSize", required = false) Integer pageSize
                                                        ) throws Exception {

        return ResponseEntity.ok(studentService.search(id, name, age, null, page, pageSize));
    }

    @PostMapping("/create2")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto> createTimeout(@RequestBody RequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(new ResponseDto());
    }


}
