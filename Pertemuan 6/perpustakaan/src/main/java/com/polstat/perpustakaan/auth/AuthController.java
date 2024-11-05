package com.polstat.perpustakaan.auth;

import com.polstat.perpustakaan.dto.UserDto;
import com.polstat.perpustakaan.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            String accessToken = jwtUtil.generateAccessToken(authentication);
            AuthResponse response = new AuthResponse(request.getEmail(),
                    accessToken);
            return ResponseEntity.ok().body(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto request) {
        UserDto user = userService.createUser(request);
        return ResponseEntity.ok().body(user);
    }
}