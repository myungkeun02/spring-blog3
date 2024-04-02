package org.myungkeun.spring_blog3.payload.authLogin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.myungkeun.spring_blog3.entities.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthLoginResponse {
    private String email;
    private String username;
    private Role role;
    private String accessToken;
    private String refreshToken;
}
