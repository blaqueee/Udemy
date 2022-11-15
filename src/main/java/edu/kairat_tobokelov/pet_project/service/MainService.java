package edu.kairat_tobokelov.pet_project.service;

import edu.kairat_tobokelov.pet_project.dto.UserDto;
import edu.kairat_tobokelov.pet_project.dto.form.RegisterRequest;
import edu.kairat_tobokelov.pet_project.entity.User;
import edu.kairat_tobokelov.pet_project.exception.EmailAlreadyExistsException;
import edu.kairat_tobokelov.pet_project.mapper.UserMapper;
import edu.kairat_tobokelov.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public UserDto register(RegisterRequest registerForm) throws EmailAlreadyExistsException, IllegalArgumentException {
        if (userRepository.existsByEmail(registerForm.getEmail()))
            throw new EmailAlreadyExistsException("Email already exists!");
        registerForm.setPassword(encoder.encode(registerForm.getPassword()));
        registerForm.getType().castByType(registerForm);
        User user = userMapper.toUser(registerForm);
        User savedUser = userRepository.save(user);
        return userMapper.toUserDto(savedUser);
    }
}
