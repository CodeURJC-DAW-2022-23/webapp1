package net.daw.alist.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.daw.alist.models.Topic;
import net.daw.alist.repositories.TopicRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TopicService {

    @Autowired
    TopicRepository topicRepository;

    public List<String> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream().map(Topic::getName).toList();
    }

    public List<Topic> getTopics(List<String> topicNames) {
        List<Topic> topics = new ArrayList<>();
        for (String topicName : topicNames) {
            Topic topic = topicRepository.findByName(topicName).orElseThrow();
            topics.add(topic);
        }
        return topics;
    }

    public boolean checkChosenName(String name){
        List<String> listTopics = this.getAllTopics();
        if (listTopics.contains(name)){
            return true;
        }
        else { return false;}
    }

    public Optional<Topic> findByName(String name){return topicRepository.findByName(name);}
    public List<Topic> findAll() {
        return topicRepository.findAll();
    }

    public Optional<Topic> findById(long id) {
        return topicRepository.findById(id);
    }

    public void delete(Topic topic) {
        topicRepository.delete(topic);
    }

    public void save(Topic topic) {
        topicRepository.save(topic);
    }

    public List<Topic> findByNameStartingWith(String prefix){
        return topicRepository.findByNameStartingWith(prefix);
    }
}
