package com.dev.backendspringstudents.repositories;

import com.dev.backendspringstudents.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface StudentRepository extends JpaRepository <Student, Long> {
}
