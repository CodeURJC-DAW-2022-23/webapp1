package net.daw.alist.repositories;

import net.daw.alist.models.Topic;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
  List<Topic> findAll();

  Optional<Topic> findByName(String name);
}
