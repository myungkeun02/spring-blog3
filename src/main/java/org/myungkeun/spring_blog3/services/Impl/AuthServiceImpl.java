package org.myungkeun.spring_blog3.services.Impl;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.myungkeun.spring_blog3.entities.Role;
import org.myungkeun.spring_blog3.entities.Token;
import org.myungkeun.spring_blog3.entities.TokenType;
import org.myungkeun.spring_blog3.entities.User;
import org.myungkeun.spring_blog3.jwt.JwtService;
import org.myungkeun.spring_blog3.payload.authLogin.AuthLoginRequest;
import org.myungkeun.spring_blog3.payload.authLogin.AuthLoginResponse;
import org.myungkeun.spring_blog3.payload.authRegister.AuthRegisterRequest;
import org.myungkeun.spring_blog3.payload.authRegister.AuthRegisterResponse;
import org.myungkeun.spring_blog3.repositories.TokenRepository;
import org.myungkeun.spring_blog3.repositories.UserRepository;
import org.myungkeun.spring_blog3.services.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthLoginResponse login(AuthLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("UserNotFound"));

        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, accessToken, refreshToken);

        AuthLoginResponse response = new AuthLoginResponse();

        response.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return response;
    }


    @Override
    public AuthRegisterResponse register(AuthRegisterRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        AuthRegisterResponse response = new AuthRegisterResponse();
        response.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .role(user.getRole());
        return response;
    }

    private void saveUserToken(User user, String accessToken, String refreshToken) {
        var token = Token.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserToken.isEmpty()) {
            return;
        }
        validUserToken.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserToken);
    }
}
}
