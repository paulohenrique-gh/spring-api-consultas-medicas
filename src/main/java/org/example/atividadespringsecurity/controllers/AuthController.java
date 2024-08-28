package org.example.atividadespringsecurity.controllers;

import jakarta.validation.Valid;
import org.example.atividadespringsecurity.domain.GenericResponseDTO;
import org.example.atividadespringsecurity.domain.user.AuthDTO;
import org.example.atividadespringsecurity.domain.user.LoginResponseDTO;
import org.example.atividadespringsecurity.domain.user.User;
import org.example.atividadespringsecurity.domain.user.UserRegisterDTO;
import org.example.atividadespringsecurity.infra.security.TokenService;
import org.example.atividadespringsecurity.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final TokenService tokenService;

    AuthController(AuthService authService, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponseDTO> register(@RequestBody UserRegisterDTO userData) {
        if (this.authService.loadUserByUsername(userData.username()) != null) {
            return ResponseEntity.badRequest().body(new GenericResponseDTO("User already exists"));
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(userData.password());
        User user = User.builder()
                .username(userData.username())
                .password(encryptedPassword)
                .role(userData.role())
                .build();
        this.authService.saveUser(user);

        return ResponseEntity.ok(new GenericResponseDTO("User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDTO userData) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userData.username(), userData.password());
        var authentication = this.authenticationManager.authenticate(usernamePassword);
        var token = this.tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
