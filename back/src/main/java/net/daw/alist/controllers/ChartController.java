package net.daw.alist.controllers;

import java.util.*;

import net.daw.alist.models.Post;
import net.daw.alist.models.Topic;
import net.daw.alist.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChartController {

    @Autowired
    private PostService postService;

    @GetMapping("/chart")
    public List<Object> calculateChart() {

        List<Object> counters = calculateUsedTopics();
        return calculateUsedTopics();
    }

    private List<Object> calculateUsedTopics(){
        List<Object> counter = new ArrayList<>();
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
        Set<String> keySet = map.keySet();
        for(String string: keySet){
            counter.add(string);
            counter.add(map.get(string));
        }
        return counter;
    }

}