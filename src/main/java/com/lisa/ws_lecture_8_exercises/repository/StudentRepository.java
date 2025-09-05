package com.lisa.ws_lecture_8_exercises.repository;

import com.lisa.ws_lecture_8_exercises.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findById(long id);
    Optional<Student> findByEmail(String email);
}