package net.daw.alist.controllers.rest;

import net.daw.alist.models.Topic;
import net.daw.alist.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/topics")
public class TopicRestController {

  @Autowired
  private TopicService topicService;

  @PostMapping("/")
  @ResponseStatus(HttpStatus.CREATED)
  public Topic createTopic(Topic topic) {
    topicService.save(topic);
    return topic;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Topic> getTopic(@PathVariable long id) {
    Optional<Topic> optionalTopic = topicService.findById(id);
    if (optionalTopic.isPresent()) {
      Topic topic = optionalTopic.get();
      return new ResponseEntity<>(topic, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Topic> deleteTopic(@PathVariable long id) {
    Optional<Topic> optionalTopic = topicService.findById(id);
    if (optionalTopic.isPresent()) {
      Topic topic = optionalTopic.get();
      topicService.delete(topic);
      return new ResponseEntity<>(HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

}
