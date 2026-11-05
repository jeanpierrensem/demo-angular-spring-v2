package com.dev.backendspringstudents;

import com.dev.backendspringstudents.domain.Payment;
import com.dev.backendspringstudents.domain.PaymentStatus;
import com.dev.backendspringstudents.domain.PaymentType;
import com.dev.backendspringstudents.domain.Student;
import com.dev.backendspringstudents.repositories.PaymentRepository;
import com.dev.backendspringstudents.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class BackendSpringStudentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendSpringStudentsApplication.class, args);
    }

    @Bean
    CommandLineRunner start(StudentRepository studentRepository, PaymentRepository paymentRepository){
        return args -> {
            studentRepository.save(Student.builder()
                            .code(UUID.randomUUID().toString())
                            .firstname("Moussa")
                            .lastname("robert")
                            .program("IBDCC")

                    .build());
            studentRepository.save(Student.builder()
                    .code(UUID.randomUUID().toString())
                    .firstname("Yassine")
                    .lastname("alpha")
                    .program("Machine Learning ")
                    .build());

            studentRepository.save(Student.builder()
                    .code(UUID.randomUUID().toString())
                    .firstname("Hafsa")
                    .lastname("thomas")
                    .program("Artificial intelligence")
                    .build());

            studentRepository.findAll().forEach( student -> {

                        for (int i = 0; i < 10 ; i++) {
                              paymentRepository.save(Payment.builder()
                                    .id(UUID.randomUUID().toString())
                                    .date(LocalDateTime.now())
                                    .student(student)
                                    .type(Math.random() > 0.5 ? PaymentType.TRANSFER : PaymentType.CASH)
                                    .amount(500 + Math.random() * 50000)
                                    .file(null)
                                    .status(PaymentStatus.CREATED)
                                    .build());

                        }

                }





            );

        };

    }

}
