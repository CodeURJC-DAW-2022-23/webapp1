package net.daw.alist.repositories;

import net.daw.alist.models.Post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

  @Query(value="select * from post where post.author_id in (select following_id from user_following where user_following.followers_id=?1) order by post.date desc", nativeQuery=true)
  Page<Post> findPostsByFollows(int user_id, Pageable page);

  @Query(value="select * from post where post.author_id=?1 order by post.date desc;", nativeQuery=true)
  Page<Post> findPostsByUser(int user_id, Pageable page);

}
