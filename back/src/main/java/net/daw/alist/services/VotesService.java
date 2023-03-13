package net.daw.alist.services;


import lombok.AllArgsConstructor;
import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.repositories.PostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class VotesService {
    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void actionUpVote(Long postID, User user){
        Post post = postRepository.findById(postID).orElseThrow();
        if(post.getUpVotes().contains(user))
            post.removeUpVote(user);
        else{
            post.addUpVote(user);
            if (post.getDownVotes().contains(user))
                post.removeDownVote(user);
        }
        post.updateVotes();
        postRepository.save(post);
    }
    @Transactional
    public void actionDownVote(Long postID, User user){
        Post post = postRepository.findById(postID).orElseThrow();
        if (post.getDownVotes().contains(user))
            post.removeDownVote(user);
        else {
            post.addDownVote(user);
            if (post.getUpVotes().contains(user))
                post.removeUpVote(user);
        }
        post.updateVotes();
        postRepository.save(post);
    }

    public void isVoted(Model model, Post post, Authentication authentication)
    {
        User userProfile = (User) userService.loadUserByUsername(((User) authentication  //authentication no devuelve el mismo objeto que hay en la base de datos. Por tanto no se puede usar la funcion contains
                .getPrincipal())
                .getUsername());
        model.addAttribute("upVoted",post.getUpVotes().contains(userProfile));
        model.addAttribute("downVoted",post.getDownVotes().contains(userProfile));
    }


}
