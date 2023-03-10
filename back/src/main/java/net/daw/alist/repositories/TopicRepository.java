package net.daw.alist.repositories;

import net.daw.alist.models.Topic;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TopicRepository extends JpaRepository<Topic, Long> {

  List<Topic> findAll();

  Optional<Topic> findByName(String name);


    @Query(value = "SELECT name FROM Topic t")
    List<String> findAllTopicNames();
}
