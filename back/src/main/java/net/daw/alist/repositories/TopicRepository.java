package net.daw.alist.repositories;

import net.daw.alist.models.Topic;

import java.util.List;
import java.util.Optional;

import net.daw.alist.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TopicRepository extends JpaRepository<Topic, Long> {

  List<Topic> findAll();

  List<Topic> findByNameStartingWith(String prefix);
  Optional<Topic> findByName(String name);

    @Query(value = "SELECT name FROM Topic t")
    List<String> findAllTopicNames();
}
