package net.daw.alist.controllers;

import net.daw.alist.models.User;
import net.daw.alist.services.UserService;
import net.daw.alist.services.VotesService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class VotesController {

    @Autowired
    VotesService votesService;
    @Autowired
    UserService userService;

    @GetMapping ("/post/{id}/upvote-action")
    public String actionUpVote(Authentication authentication, @PathVariable Long id){
        Long postID = id;
        if(!(authentication == null)) {
            User userSession = (User) userService.loadUserByUsername(((User) authentication
                    .getPrincipal())
                    .getUsername());
            votesService.actionUpVote(postID, userSession);
        }
        else
            return "redirect:/sign-in";
        return "redirect:/feed";
    }

    @GetMapping("/post/{id}/downvote-action")
    @ResponseStatus(value = HttpStatus.OK)
    public String actionDownVote(Authentication authentication, @PathVariable Long id){
        Long postID = id;
        if(!(authentication == null)) {
            User userSession = (User) userService.loadUserByUsername(((User) authentication
                    .getPrincipal())
                    .getUsername());
            votesService.actionDownVote(postID, userSession);
        }
        else
            return "redirect:/sign-in";
        return "redirect:/feed";
    }


}
