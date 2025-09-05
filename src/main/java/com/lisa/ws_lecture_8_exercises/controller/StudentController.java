package com.lisa.ws_lecture_8_exercises.controller;

import com.lisa.ws_lecture_8_exercises.model.Student;
import com.lisa.ws_lecture_8_exercises.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/")
    public ResponseEntity<List<Student>> findAll() {

        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Student>> findById(@PathVariable Long id) {

        Optional<Student> student = studentRepository.findById(id);

        if (student.isPresent()) {
            return ResponseEntity.ok(student);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent(@Valid @RequestBody Student customStudent) {

        if (studentRepository.findByEmail(customStudent.getEmail()).isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Student savedStudent = studentRepository.save(customStudent);
        return ResponseEntity.ok(savedStudent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody Student customStudent) {

        Optional<Student> foundUser = studentRepository.findById(id);

        if(foundUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Student studentToBeUpdated = foundUser.get();

        studentToBeUpdated.setFirstName(customStudent.getFirstName());
        studentToBeUpdated.setLastName(customStudent.getLastName());
        studentToBeUpdated.setEmail(customStudent.getEmail());

        Student updatedUser = studentRepository.save(studentToBeUpdated);

        return ResponseEntity.ok(updatedUser);

    }
}