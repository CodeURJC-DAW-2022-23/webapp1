package net.daw.alist.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class PasswordEncoder {
    private BCryptPasswordEncoder encoder;

    public void PasswordEncoder(){
        this.encoder = new BCryptPasswordEncoder(10);
    }

    public String encode(String password){
        return encoder.encode(password);
    }

}
