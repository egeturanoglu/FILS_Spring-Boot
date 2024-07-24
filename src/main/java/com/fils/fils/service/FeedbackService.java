package com.fils.fils.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fils.fils.model.Feedback;
import com.fils.fils.repository.FeedbackRepository;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public void deleteFeedbackById(Long id) {
        feedbackRepository.deleteById(id);
    }

    public void deleteAllFeedback() {
        feedbackRepository.deleteAll();
    }
}
