package net.daw.alist.services;

import lombok.AllArgsConstructor;
import net.daw.alist.models.User;
import net.daw.alist.repositories.UserRepository;
import net.daw.alist.security.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException{
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    private boolean emailValid(String emailAddress){
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(regexPattern).matcher(emailAddress).matches();
    }
    private String errorChecks(User user){

        //User not formatted correctly
        if(user.getUsername().length() < 4)
            return "Username must have at least 4 characters";
        if(user.getPassword().length() < 4)
            return "Password must have at least 4 characters";
        if(!emailValid(user.getEmail()))
            return "Invalid email address";

        //User formatted correctly
        boolean usernameTaken = userRepository
                .findByUsername(user.getUsername())
                .isPresent();

        boolean emailTaken = userRepository
                .findByEmail(user.getEmail())
                .isPresent();

        if (usernameTaken && emailTaken) {
            return "User already registered";
        } else if (usernameTaken){
            return "Username already taken";
        } else if(emailTaken) {
            return "Email address already taken";
        }

        //All checks passed
        return "Success";
    }

    public String register(User user) {

        String errorMessage = errorChecks(user);

        if (!errorMessage.equals("Success")){
            return errorMessage;
        }

        String encodedPassword = bCryptPasswordEncoder()
                .encode(user.getPassword());

        user.setPassword(encodedPassword);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        return "token=" + token;
    }

    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    public void unbanUser(String username) {
        userRepository.unbanUser(username);
	}

    public void banUser(String username) {
        userRepository.banUser(username);
    }

    public User findByUsername(String string) {
      return userRepository.findByUsername(string).orElseThrow();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow();
    }
}
