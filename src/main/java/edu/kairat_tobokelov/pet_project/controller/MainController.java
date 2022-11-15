package edu.kairat_tobokelov.pet_project.controller;

import edu.kairat_tobokelov.pet_project.dto.form.LoginRequest;
import edu.kairat_tobokelov.pet_project.dto.form.RegisterRequest;
import edu.kairat_tobokelov.pet_project.dto.form.ResetPasswordRequest;
import edu.kairat_tobokelov.pet_project.exception.EmailAlreadyExistsException;
import edu.kairat_tobokelov.pet_project.jwt.CustomUserDetails;
import edu.kairat_tobokelov.pet_project.service.MainService;
import edu.kairat_tobokelov.pet_project.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {
    private final MainService mainService;
    private final PasswordService passwordService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            return ResponseEntity.ok(mainService.register(registerRequest));
        } catch (IllegalArgumentException | EmailAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(mainService.login(loginRequest));
    }

    @GetMapping("/user") // route for testing jwt access token depending on role
    public ResponseEntity<String> userRoute(Authentication authentication) {
        var user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok("hello user");
    }

    @GetMapping("/mentor") // route for testing jwt access token depending on role
    public ResponseEntity<String> mentorRoute(Authentication authentication) {
        var mentor = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok("hello mentor");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> sendMailWithPasswordResetUrl(@RequestParam(defaultValue = "none") String email) {
        if ("none".equals(email)) return ResponseEntity.badRequest().body("Empty email!");
        return passwordService.sendMailWithUrl(email);
    }

    @PostMapping("/reset/{resetCode}")
    public ResponseEntity<String> resetPassword(@PathVariable(name = "resetCode") String resetCode,
                                                @Valid @RequestBody ResetPasswordRequest resetForm) {
        if (!resetForm.getPassword().equals(resetForm.getRepeatedPassword()))
            return ResponseEntity.badRequest().body("Your repeated password is not similar with password!");
        return passwordService.resetPassword(resetCode, resetForm.getPassword());
    }
}
