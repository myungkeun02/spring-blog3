package org.myungkeun.spring_blog3.controllers;

import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_blog3.payload.authLogin.AuthLoginRequest;
import org.myungkeun.spring_blog3.payload.authLogin.AuthLoginResponse;
import org.myungkeun.spring_blog3.payload.authRegister.AuthRegisterRequest;
import org.myungkeun.spring_blog3.payload.authRegister.AuthRegisterResponse;
import org.myungkeun.spring_blog3.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final AuthService authService;

    // register logic
    @PostMapping("/register")
    public ResponseEntity<AuthRegisterResponse> register(AuthRegisterRequest request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    // login logic
    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponse> login(AuthLoginRequest request) {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK)

    }
}
