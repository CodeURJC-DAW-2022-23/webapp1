package net.daw.alist.services;

import lombok.AllArgsConstructor;
import net.daw.alist.models.Post;
import net.daw.alist.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    @Autowired
    PostRepository postRepository;

    public void save(Post post) {
        postRepository.save(post);
    }

}
