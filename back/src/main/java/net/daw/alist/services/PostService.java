package net.daw.alist.services;

import lombok.AllArgsConstructor;
import net.daw.alist.models.Post;
import net.daw.alist.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    public Page<Post> getPosts(int number) {
        return postRepository.findAll(PageRequest.of(0, number));
    }

}
