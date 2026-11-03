package com.dev.backendspringstudents.repositories;

import com.dev.backendspringstudents.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PaymentRepository extends JpaRepository<Payment, String>  {
  List<Payment> findAllById(String paymentId);
}
