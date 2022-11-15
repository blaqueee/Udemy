package edu.kairat_tobokelov.pet_project.dto.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    @NotNull
    private String password;

    @NotNull
    @JsonProperty("repeated_password")
    private String repeatedPassword;
}
