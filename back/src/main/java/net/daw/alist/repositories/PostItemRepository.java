package net.daw.alist.repositories;

import net.daw.alist.models.PostItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostItemRepository extends JpaRepository<PostItem, Long> {
}
