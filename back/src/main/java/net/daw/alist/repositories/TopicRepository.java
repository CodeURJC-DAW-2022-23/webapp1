package net.daw.alist.repositories;

import net.daw.alist.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    @Query(value = "SELECT name FROM Topic t")
    List<String> findAllTopicNames();
}
