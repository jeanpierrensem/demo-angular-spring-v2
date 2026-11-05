package com.dev.backendspringstudents.repositories;

import com.dev.backendspringstudents.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;



public interface StudentRepository extends JpaRepository <Student, Long> {

    Student findStudentByCode(String code);
}
