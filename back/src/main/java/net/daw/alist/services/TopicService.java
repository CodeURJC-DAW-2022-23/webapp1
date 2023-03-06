package net.daw.alist.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.daw.alist.models.Topic;
import net.daw.alist.repositories.TopicRepository;

@Service
@AllArgsConstructor
public class TopicService{

  @Autowired
  private final TopicRepository topicRepository;

  public List<Topic> findAll() {
    return topicRepository.findAll();
  }

  public Topic findById(long id) {
    return topicRepository.findById(id).orElseThrow();
  }

  public void delete(Topic topic) {
    topicRepository.delete(topic);
  }

  public void save(Topic topic) {
    topicRepository.save(topic);
  }
  
}
