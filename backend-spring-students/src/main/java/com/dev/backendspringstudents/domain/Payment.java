package com.dev.backendspringstudents.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString @EqualsAndHashCode
public class Payment {
   @Id
    private String id;
    private LocalDateTime date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentType type;
    private String file ;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
