package edu.kairat_tobokelov.pet_project.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kairat_tobokelov.pet_project.entity.Audience;
import edu.kairat_tobokelov.pet_project.entity.Experience;
import edu.kairat_tobokelov.pet_project.entity.Type;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull
    @JsonProperty("first_name")
    private String firstName;

    @NotNull
    @JsonProperty("last_name")
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Type type;

    private Experience experience;

    private Audience audience;
}
