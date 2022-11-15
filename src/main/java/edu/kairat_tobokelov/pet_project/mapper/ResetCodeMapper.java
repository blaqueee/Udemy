package edu.kairat_tobokelov.pet_project.mapper;

import edu.kairat_tobokelov.pet_project.entity.ResetCode;
import edu.kairat_tobokelov.pet_project.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ResetCodeMapper {
    public ResetCode toResetCode(User user, String code) {
        return ResetCode.builder()
                .user(user)
                .code(code)
                .generatedTime(LocalDateTime.now())
                .build();
    }
}
