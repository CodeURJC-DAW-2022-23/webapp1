package net.daw.alist.services;

import lombok.AllArgsConstructor;
import net.daw.alist.models.Comment;
import net.daw.alist.models.Post;
import net.daw.alist.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
        return postRepository.findAll(PageRequest.of(pageNumber, pageSize,Sort.by("votes").descending()));
    }

    public List<Comment> getNewComments(Long id, int pageNumber) {
        List<Comment> comments = postRepository.getReferenceById(id).getComments();
        int start = pageSize * pageNumber;
        int end = start + pageSize;
        if (end > comments.size()){
            end = comments.size();
        }
        return comments.subList(start, end);
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

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public long count() {
        return postRepository.count();
    }

  public Optional<Comment> getCommentByID(Post post, long commentId) {
    List<Comment> comments = post.getComments();
    for (Comment comment : comments) {
      if (comment.getId() == commentId) {
        return Optional.of(comment);
      }
    }
    return Optional.empty();
  }
}
