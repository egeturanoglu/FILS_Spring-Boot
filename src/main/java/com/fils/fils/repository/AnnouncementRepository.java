package com.fils.fils.repository;

import com.fils.fils.model.Announcement;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findAllByOrderByIdDesc();
}

