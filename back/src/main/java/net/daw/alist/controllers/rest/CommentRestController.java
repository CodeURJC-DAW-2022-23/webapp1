package net.daw.alist.controllers.rest;

import net.daw.alist.models.Comment;
import net.daw.alist.models.Post;
import net.daw.alist.models.User;
import net.daw.alist.services.PostService;
import net.daw.alist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {
    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @PostMapping("/{postId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(Comment comment, @PathVariable long postId) {
        Optional<Post> optionalPost = postService.findByID(postId);
        if(optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.addComment(comment);
            postService.save(post);
            return comment;
        }
        return null;
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getPostComments(@PathVariable long postId) {
        Optional<Post> optionalPost = postService.findByID(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            List<Comment> comments = post.getComments();
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getUserComments(@PathVariable long userId) {
        Optional<User> optionalUser = userService.findByID(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Comment> comments = user.getComments();
            return new ResponseEntity<>(comments, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{postId}/{commentId}")
    public ResponseEntity<Post> deletePost(@PathVariable long postId, @PathVariable long commentId) {
        Optional<Post> optionalPost = postService.findByID(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Optional<Comment> optionalComment = post.getCommentByID(commentId);
            if(optionalComment.isPresent()){
                Comment comment = optionalComment.get();
                post.getComments().remove(comment);
                postService.save(post);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
