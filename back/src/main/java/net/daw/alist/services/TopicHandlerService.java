package net.daw.alist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.daw.alist.models.Topic;
import net.daw.alist.repositories.TopicRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;




@Service
public class TopicHandlerService {
    @Autowired
    TopicRepository topic;
    
    public List<String> topicGetter()
    {
        List<Topic> topics;
        List<String> result = new ArrayList<>();
        topics = topic.findAll();

        for (Topic iterator : topics) {
            result.add(iterator.getName());
        }

        return result;
    }
    
    public List<Topic> topicList(List<String> list)
    {
        List<Topic> result = new ArrayList<>();
        Optional<Topic> topicOption;
        Topic iterator;

        for (String name : list) {
            topicOption = topic.findByName(name);
            iterator = topicOption.get();
            result.add(iterator);
        }
        
        return result;
    }
}