package net.daw.alist.services;

import net.daw.alist.models.Post;
import net.daw.alist.models.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Service
public class ChartService {

    @Autowired
    private PostService postService;

    @GetMapping("/chart")

    public Map<String,Integer> getTopicsMapped(){
        List<Post> posts = postService.findAll();
        Map<String, Integer> map = new HashMap<>();
        for (Post post:posts) {
            List<Topic> topics = post.getTopics();
            for (Topic topic:topics) {
                if(map.containsKey(topic.getName())){
                    Integer repetition = map.get(topic.getName());
                    map.put(topic.getName(), repetition+1);
                }else{
                    map.put(topic.getName(), 1);
                }
            }
        }
        return map;
    }
}
