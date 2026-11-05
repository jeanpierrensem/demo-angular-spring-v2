package com.dev.backendspringstudents.web;

import com.dev.backendspringstudents.domain.Payment;
import com.dev.backendspringstudents.domain.PaymentStatus;
import com.dev.backendspringstudents.domain.PaymentType;
import com.dev.backendspringstudents.domain.Student;
import com.dev.backendspringstudents.repositories.PaymentRepository;
import com.dev.backendspringstudents.repositories.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController("/api")
public class StudentRestController {

    StudentRepository  studentRepository;
    PaymentRepository paymentRepository ;

    public StudentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents () {
        return studentRepository.findAll();
    }

   @GetMapping("/students/{code}")
    public Student getStudentByCode(@PathVariable("code") String studentCode) {
        return studentRepository.findStudentByCode(studentCode);
    }
    @GetMapping("/students/{code}/payments")
    public List<Payment> getAllStudentPayments(@PathVariable("code") String studentCode) {
        return paymentRepository.findPaymentsByStudentCode(studentCode);
    }
    @GetMapping("/payments")
  public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
  }
    @GetMapping("/payments/{id}")
  Payment getPaymentById(@PathVariable("id") String paymentId){
        return paymentRepository.findById(paymentId).get();
  }

  @PostMapping(value = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  Payment savePayment( @RequestParam("file") MultipartFile file, double amount,  Long studentId) throws IOException {

        Student student  = studentRepository.findById(studentId).get();

      String paymentId = UUID.randomUUID().toString();
      Path path = Paths.get(System.getProperty("user.home"), "students-app-files","payments");
      if (!Files.exists(path)){
          Files.createDirectories(path);
      }
      Path filePath = Paths.get(System.getProperty("user.home"), "students-app-files", "payments",  paymentId.concat(".pdf"));
      Files.copy(file.getInputStream(), filePath);
      return  paymentRepository.save(Payment.builder()
              .id(paymentId)
              .amount(amount)
              .date(LocalDateTime.now())
                        .student(student)
              .status(PaymentStatus.CREATED)
              .type(Math.random() > 0.5 ? PaymentType.CASH : PaymentType.TRANSFER)
              .file(filePath.toUri().toString())
              .build());
  }

  @GetMapping(value = "/payments/file/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
  byte [] getPaymentFile(@PathVariable String id) throws IOException {
        Payment payment = paymentRepository.findById(id).get();

        String filePath = payment.getFile();
        if (filePath==null || filePath.isBlank() ) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "No File found for this Payment");
        }
      Path path = filePath.startsWith("file:") ? Path.of(URI.create(filePath)) : Path.of(filePath);

        if(!Files.exists(path)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File Not found on the Disk");
        }
      return Files.readAllBytes(path);
    }

  @PostMapping("/payments/{id}/status")
  Payment setStatus(@PathVariable("id") String paymentId, PaymentStatus status){
        Payment payment  = paymentRepository.findById(paymentId).get();
        payment.setStatus(status);
      return  paymentRepository.save(payment);
  }
}
