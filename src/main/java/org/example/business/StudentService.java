package org.example.business;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.exceptions.NotFoundException;
import org.example.model.StudentRequest;
import org.example.model.StudentResponse;
import org.example.repo.StudentEntity;
import org.example.repo.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentResponse create(StudentRequest request){
        StudentEntity entity = new StudentEntity();
        BeanUtils.copyProperties(request, entity);
        entity = studentRepository.save(entity);
        StudentResponse response = new StudentResponse();
        BeanUtils.copyProperties(entity, response);
       return response;
    }

    public StudentResponse find(Long id){
        StudentEntity entity = new StudentEntity();
        Optional<StudentEntity> studentEntity = studentRepository.findById(id);
//        Consumer<String> f1 = NotFoundException:: new;
//        f1.accept("Student not found");
        studentEntity.orElseThrow(() -> new NotFoundException("Student not found"));
        StudentResponse response = new StudentResponse();
        BeanUtils.copyProperties(studentEntity.get(), response);
       return response;
    }
}
