package net.daw.alist.services;

import lombok.AllArgsConstructor;
import net.daw.alist.models.UserRole;
import net.daw.alist.repositories.UserRepository;
import net.daw.alist.security.RegistrationRequest;
import net.daw.alist.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    @Autowired
    private final UserService userService;

    @Autowired
    private UserRepository userRepository;

    public String register(RegistrationRequest request) {

        String token = userService.register(
                new User(
                        request.getUsername(),
                        request.getPassword(),
                        request.getEmail(),
                        UserRole.USER,
                        null

                )
        );
        /*String link = "http://localhost:8443/register/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(), link));*/


        return token;
    }
}
