package edu.kairat_tobokelov.pet_project.service;

import edu.kairat_tobokelov.pet_project.entity.ResetCode;
import edu.kairat_tobokelov.pet_project.entity.User;
import edu.kairat_tobokelov.pet_project.mapper.ResetCodeMapper;
import edu.kairat_tobokelov.pet_project.repository.ResetCodeRepository;
import edu.kairat_tobokelov.pet_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordService {
    private final UserRepository userRepository;
    private final ResetCodeRepository resetCodeRepository;
    private final ResetCodeMapper resetCodeMapper;
    private final PasswordEncoder encoder;
    private final MailSenderService mailSender;


    public ResponseEntity<String> sendMailWithUrl(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty())
            return ResponseEntity.badRequest().body("User with this email doesn't exist!");
        String code = createCode(email);
        ResetCode resetCode = resetCodeMapper.toResetCode(optionalUser.get(), code);
        resetCodeRepository.save(resetCode);
        mailSender.sendMessage(email, "Reset your password!", "Go to the link below, to reset your password!\n" +
                "http://localhost:8080/reset/" + code);
        return ResponseEntity.ok("Check your email!");
    }

    public ResponseEntity<String> resetPassword(String code, String password) {
        code = URLEncoder.encode(code);
        Optional<ResetCode> resetCode = resetCodeRepository.findByCode(code);
        if (!isCodeValid(resetCode))
            return ResponseEntity.badRequest().body("Link doesn't exist or expired!");
        User user = resetCode.get().getUser();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        resetCodeRepository.delete(resetCode.get());
        mailSender.sendMessage(user.getEmail(), "Success!", "Your password successfully updated!");
        return ResponseEntity.ok("Password successfully updated!");
    }

    private boolean isCodeValid(Optional<ResetCode> resetCode) {
        if (resetCode.isEmpty())
            return false;
        ResetCode code = resetCode.get();
        if (code.getGeneratedTime().plusHours(24).isBefore(LocalDateTime.now()))
            return false;
        return true;
    }

    private String createCode(String email) {
        String code = encoder.encode(email);
        code = code.replace("/", "");
        code = code.replace("\\", "");
        return URLEncoder.encode(code);
    }
}
