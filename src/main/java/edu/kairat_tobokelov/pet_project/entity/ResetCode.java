package edu.kairat_tobokelov.pet_project.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reset_codes")
public class ResetCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "generated_time")
    private LocalDateTime generatedTime;
}
