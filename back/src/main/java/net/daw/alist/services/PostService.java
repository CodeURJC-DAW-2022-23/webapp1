package net.daw.alist.services;

import lombok.AllArgsConstructor;
import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {

    @Autowired
    private final PostRepository postRepository;

    private final int pageSize = 2;

    public Page<Post> getPosts(int pageNumber) {
        return postRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public Page<Post> getUserPosts(int pageNumber, int user_id) {
        return postRepository.findPostsByUser(user_id, PageRequest.of(pageNumber, pageSize));
    }

    public Page<Post> getStarredPosts(int pageNumber, int user_id) {
        return postRepository.findPostsByFollows(user_id, PageRequest.of(pageNumber, pageSize));
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findByID(Long id){
        return postRepository.findById(id);
    }

    public void save(Post post) {
        postRepository.save(post);
    }

}
