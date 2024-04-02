package org.myungkeun.spring_blog3.services;

import org.myungkeun.spring_blog3.payload.authLogin.AuthLoginRequest;
import org.myungkeun.spring_blog3.payload.authLogin.AuthLoginResponse;
import org.myungkeun.spring_blog3.payload.authRegister.AuthRegisterRequest;
import org.myungkeun.spring_blog3.payload.authRegister.AuthRegisterResponse;

public interface AuthService {
    AuthLoginResponse login(AuthLoginRequest request);
    AuthRegisterResponse register(AuthRegisterRequest request);
}
