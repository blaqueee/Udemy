package edu.kairat_tobokelov.pet_project.service;

import edu.kairat_tobokelov.pet_project.dto.JwtDto;
import edu.kairat_tobokelov.pet_project.dto.UserDto;
import edu.kairat_tobokelov.pet_project.dto.form.LoginRequest;
import edu.kairat_tobokelov.pet_project.dto.form.RegisterRequest;
import edu.kairat_tobokelov.pet_project.entity.User;
import edu.kairat_tobokelov.pet_project.exception.EmailAlreadyExistsException;
import edu.kairat_tobokelov.pet_project.jwt.CustomUserDetails;
import edu.kairat_tobokelov.pet_project.jwt.JwtUtils;
import edu.kairat_tobokelov.pet_project.mapper.UserMapper;
import edu.kairat_tobokelov.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UserDto register(RegisterRequest registerForm) throws EmailAlreadyExistsException, IllegalArgumentException {
        if (userRepository.existsByEmail(registerForm.getEmail()))
            throw new EmailAlreadyExistsException("Email already exists!");
        registerForm.setPassword(encoder.encode(registerForm.getPassword()));
        registerForm.getType().castByType(registerForm);
        User user = userMapper.toUser(registerForm);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }

    public JwtDto login(LoginRequest loginRequest) {
        Authentication auth = getAuthenticationFromLoginRequest(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtUtils.generateJwtToken(auth);
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userMapper.toJwtResponseDto(userDetails, token);
    }

    private Authentication getAuthenticationFromLoginRequest(LoginRequest loginRequest) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
    }
}
