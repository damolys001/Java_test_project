package com.net.controllers;

import com.net.entities.Topic;
import com.net.repositories.AnswerRepository;
import com.net.repositories.TopicRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TopicsController {

    private final TopicRepository topicRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public TopicsController(TopicRepository topicRepository, AnswerRepository answerRepository) {
        this.topicRepository = topicRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping("topics")
    public String displayAllTopics(Model model) {
        List<Topic> topics = topicRepository.findAll(new Sort(Sort.Direction.DESC, "createdDate"));
        String header = setHeader("all");
        model.addAttribute("topics", topics);
        model.addAttribute("header", header);
        model.addAttribute("answerRepository", answerRepository);
        return "topics";
    }

    @GetMapping("topics/{category}")
    public String displayTopicsByCategory(@PathVariable String category, Model model) {
    	System.out.println("category "+category);
        List<Topic> topics = topicRepository.findTopicsByCategoryOrderByCreatedDateDesc(category);
        String header = setHeader(category);
        model.addAttribute("topics", topics);
        model.addAttribute("header", header);
        model.addAttribute("answerRepository", answerRepository);
        return "topics";
    }

    @GetMapping("topics/user/{id}")
    public String displayTopicsByUser(@PathVariable String id, Model model) {
    	System.out.println("id"+id);
        List<Topic> topics = topicRepository.findTopicsByUser_IdOrderByCreatedDateDesc(Long.valueOf(id));
        String header = setHeader("user");
        model.addAttribute("topics", topics);
        model.addAttribute("header", header);
        model.addAttribute("answerRepository", answerRepository);
        return "topics";
    }

    private String setHeader(String category) {
        switch (category) {
            case "fbn":
                return "First Bank";
            case "uni":
                return "Union Bank";
            case "wma":
                return "Wema Bank";
            case "zth":
                return "Zenith Bank";
            case "web":
                return "United bank For Africa";
            case "all":
                return "All topics";
            default:
                return "User's topics";
        }
    }
}