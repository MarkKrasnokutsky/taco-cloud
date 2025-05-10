package com.mark.taco_cloud.domain.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Builder
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    public User toUser(PasswordEncoder encoder) {
        return User.builder()
                .username(username)
                .password(encoder.encode(password))
                .fullname(fullname)
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .phoneNumber(phone)
                .build();
    }

}
