package net.daw.alist.repositories;

import net.daw.alist.models.Topic;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
  List<Topic> findAll();
}
