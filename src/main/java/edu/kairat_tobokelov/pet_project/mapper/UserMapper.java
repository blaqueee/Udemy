package edu.kairat_tobokelov.pet_project.mapper;

import edu.kairat_tobokelov.pet_project.dto.JwtDto;
import edu.kairat_tobokelov.pet_project.dto.UserDto;
import edu.kairat_tobokelov.pet_project.dto.form.RegisterRequest;
import edu.kairat_tobokelov.pet_project.jwt.CustomUserDetails;
import edu.kairat_tobokelov.pet_project.entity.Type;
import edu.kairat_tobokelov.pet_project.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User toUser(RegisterRequest registerForm) {
        var user = User.builder()
                .firstName(registerForm.getFirstName())
                .lastName(registerForm.getLastName())
                .email(registerForm.getEmail())
                .password(registerForm.getPassword())
                .type(registerForm.getType())
                .experience(registerForm.getExperience())
                .audience(registerForm.getAudience())
                .build();
        if (user.getType() == Type.ROLE_MENTOR) user.setMentor(true);
        return user;
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .type(user.getType())
                .build();
    }

    public UserDetails toUserDetails(User user) {
        return CustomUserDetails.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .type(user.getType())
                .experience(user.getExperience())
                .audience(user.getAudience())
                .isMentor(user.isMentor())
                .build();
    }

    public JwtDto toJwtResponseDto(CustomUserDetails userDetails, String token) {
        return JwtDto.builder()
                .id(userDetails.getId())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .email(userDetails.getEmail())
                .role(userDetails.getType())
                .token(token)
                .tokenType("Bearer")
                .build();
    }
}
