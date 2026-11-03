package com.dev.backendspringstudents.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString @EqualsAndHashCode
public class Student {
    @Id @GeneratedValue
    private Long id;
    private String code ;
    private String lastname;
    private String firstname;
    private String program;

}
