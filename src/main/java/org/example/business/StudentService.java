package org.example.business;

import lombok.AllArgsConstructor;
import org.example.exceptions.NotFoundException;
import org.example.model.StudentRequest;
import org.example.model.StudentResponse;
import org.example.repo.StudentEntity;
import org.example.repo.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentResponse create(StudentRequest request) {
        StudentEntity entity = new StudentEntity();
        BeanUtils.copyProperties(request, entity);
        entity = studentRepository.save(entity);
        StudentResponse response = new StudentResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public StudentResponse find(Long id) {
        StudentEntity entity = new StudentEntity();
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
//        Consumer<String> f1 = NotFoundException:: new;
//        f1.accept("Student not found");
        studentEntity.orElseThrow(() -> new NotFoundException("Student not found"));
        StudentResponse response = new StudentResponse();
        BeanUtils.copyProperties(studentEntity.get(), response);
        return response;
    }

    public List<StudentResponse> findByName(String name) {
        StudentEntity entity = new StudentEntity();
        Optional<List<StudentEntity>> studentEntity = studentRepository.findByName(name);
//        Consumer<String> f1 = NotFoundException:: new;
//        f1.accept("Student not found");
        studentEntity.orElseThrow(() -> new NotFoundException("Student not found"));
        List<StudentResponse> responses = new ArrayList<>();
        studentEntity.get().forEach(item -> {
            StudentResponse response = new StudentResponse();
            BeanUtils.copyProperties(item, response);
            responses.add(response);
        });
        return responses;
    }

    public List<StudentResponse> search(Long id, String name, Integer age, LocalDateTime localDateTime) {
        Optional<List<StudentEntity>> studentEntities = studentRepository.search(id, name, age, localDateTime);
        studentEntities.orElseThrow(() -> new NotFoundException("Student not found"));
        List<StudentResponse> responses = new ArrayList<>();
//            });
        if (!studentEntities.get().isEmpty())
            responses.addAll(studentEntities.get().stream().map(item -> {
                        StudentResponse response = new StudentResponse();
                        BeanUtils.copyProperties(item, response);
                        return response;
                    }).collect(Collectors.toList())
            );

        return responses;
    }

    public List<StudentResponse> search(Long id, String name, Integer age, LocalDateTime localDateTime, Integer page, Integer pageSize) {
        Page<StudentEntity> studentEntities = studentRepository.search(id, name, age, localDateTime, PageRequest.of(page, pageSize));
        List<StudentResponse> responses = new ArrayList<>();
            responses.addAll(studentEntities.get().map(item -> {
                        StudentResponse response = new StudentResponse();
                        BeanUtils.copyProperties(item, response);
                        return response;
                    }).collect(Collectors.toList())
            );

        return responses;
    }
}
