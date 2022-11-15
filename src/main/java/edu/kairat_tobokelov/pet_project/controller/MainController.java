package edu.kairat_tobokelov.pet_project.controller;

import edu.kairat_tobokelov.pet_project.dto.JwtResponseDto;
import edu.kairat_tobokelov.pet_project.dto.form.LoginRequest;
import edu.kairat_tobokelov.pet_project.dto.form.RegisterRequest;
import edu.kairat_tobokelov.pet_project.entity.CustomUserDetails;
import edu.kairat_tobokelov.pet_project.exception.EmailAlreadyExistsException;
import edu.kairat_tobokelov.pet_project.jwt.JwtUtils;
import edu.kairat_tobokelov.pet_project.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class MainController {
    private final MainService mainService;

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

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
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtUtils.generateJwtToken(auth);
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();


        var response = JwtResponseDto.builder()
                .id(userDetails.getId())
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .email(userDetails.getEmail())
                .role(userDetails.getType())
                .token(token)
                .tokenType("Bearer")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> userRoute(Authentication authentication) {
        var user = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok("hello user");
    }

    @GetMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public ResponseEntity<String> mentorRoute(Authentication authentication) {
        var mentor = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok("hello mentor");
    }
}
