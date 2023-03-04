package net.daw.alist.services;

import org.springframework.beans.factory.annotation.Autowired;

import net.daw.alist.models.Topic;
import net.daw.alist.repositories.TopicRepository;

import java.util.List;


public class TopicHandlerService {
    @Autowired
    TopicRepository topic;
    
    public Topic topicChecker(String topicName)
    {
        Topic result=new Topic();
        boolean found=false;
        List <Topic> topics;
        topics=topic.findAll();

        for(Topic iterator:topics)
        {
            if (iterator.getName().equals(topicName)) {
                found = true;
                result = iterator;
            }
        }
        if (!found) {

            result.setName("random");
        }
        
        return result;
    }
}