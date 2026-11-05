package com.dev.backendspringstudents.repositories;

import com.dev.backendspringstudents.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, String>  {
  List<Payment> findPaymentsByStudentCode(String studentCode);
}
