package net.daw.alist.services;

import lombok.AllArgsConstructor;
import net.daw.alist.models.UserRole;
import net.daw.alist.security.RegistrationRequest;
import net.daw.alist.models.User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final UserService userService;
    public String register(RegistrationRequest request) {
        /*boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }*/

        String token = userService.register(
                new User(
                        request.getUsername(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER,
                        null

                )
        );

        /*String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(), link));*/

        return token;
    }
}
