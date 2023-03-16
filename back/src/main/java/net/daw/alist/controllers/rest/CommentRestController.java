package net.daw.alist.controllers.rest;

import net.daw.alist.models.Comment;
import net.daw.alist.models.Post;
import net.daw.alist.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {
    @Autowired
    private PostService postService;

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

    @GetMapping("/{postId}/{commentId}")
    public ResponseEntity<Comment> getComment(@PathVariable long postId, @PathVariable long commentId) {
        Optional<Post> optionalPost = postService.findByID(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            Optional<Comment> optionalComment = post.getCommentByID(commentId);
            if(optionalComment.isPresent()){
                Comment comment = optionalComment.get();
                return new ResponseEntity<>(comment, HttpStatus.OK);
            }
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
