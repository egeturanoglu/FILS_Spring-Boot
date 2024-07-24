package com.fils.fils.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fils.fils.model.Feedback;
import com.fils.fils.service.FeedbackService;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public List<Feedback> getAllFeedback() {
        return feedbackService.getAllFeedback();
    }

    @GetMapping("/{id}")
    public Feedback getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id);
    }

    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        return feedbackService.saveFeedback(feedback);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedbackById(@PathVariable Long id) {
        feedbackService.deleteFeedbackById(id);
    }

    @DeleteMapping
    public void deleteAllFeedback() {
        feedbackService.deleteAllFeedback();
    }
}
