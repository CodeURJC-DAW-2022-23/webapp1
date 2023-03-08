package net.daw.alist.services;

import lombok.AllArgsConstructor;
import net.daw.alist.models.Post;
import net.daw.alist.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final int pageSize = 2;
    @Autowired
    private final PostRepository postRepository;

    public Page<Post> getPosts(int pageNumber) {
        return postRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }
    
    public Page<Post> getStarredPosts(int pageNumber, int user_id) {
        return postRepository.findPostsByFollows(user_id,PageRequest.of(pageNumber,pageSize));
    }
    
    public void save(Post post) {
        postRepository.save(post);
    }
}
