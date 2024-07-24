package com.fils.fils.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fils.fils.model.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
