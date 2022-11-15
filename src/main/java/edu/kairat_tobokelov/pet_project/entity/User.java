package edu.kairat_tobokelov.pet_project.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "experience", nullable = true)
    @Enumerated(EnumType.STRING)
    private Experience experience;

    @Column(name = "audience", nullable = true)
    @Enumerated(EnumType.STRING)
    private Audience audience;

    @Column(name = "is_mentor")
    @JsonProperty("is_mentor")
    private boolean isMentor;

    @Column(name = "first_name", nullable = false)
    @JsonProperty("first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonProperty("last_name")
    private String lastName;
}
