package net.daw.alist.services;


import lombok.AllArgsConstructor;
import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class VotesService {
    @Autowired
    private final PostRepository postRepository;

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


}
