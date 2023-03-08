package net.daw.alist.repositories;

import net.daw.alist.models.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
  @Query("select * from post where post.author_id in (select following_id from user_following where user_following.followers_id=?1);")
  Page<Post> findPostsByFollows(int user_id);
}
