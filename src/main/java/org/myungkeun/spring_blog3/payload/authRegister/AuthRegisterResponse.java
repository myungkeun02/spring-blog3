package org.myungkeun.spring_blog3.payload.authRegister;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.myungkeun.spring_blog3.entities.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthRegisterResponse {
    private String email;
    private String username;
    private Role role;
}
