package edu.kairat_tobokelov.pet_project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.kairat_tobokelov.pet_project.entity.Type;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtDto {
    private Long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    private Type role;

    private String token;

    @JsonProperty("token_type")
    private String tokenType;
}
