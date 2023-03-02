package net.daw.alist.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.daw.alist.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        User userDetails = (User) authentication.getPrincipal();

        String redirectURL;

        if (userDetails.getRole().name().equals("ADMIN")) {
            redirectURL = "/admin-panel";
        } else {
            redirectURL = "/";
        }

        response.sendRedirect(redirectURL);

    }

}
