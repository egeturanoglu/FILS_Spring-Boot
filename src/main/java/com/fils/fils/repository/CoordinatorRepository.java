package com.fils.fils.repository;

import com.fils.fils.model.Coordinator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatorRepository extends JpaRepository<Coordinator, Long> {
    Coordinator findByUsername(String username);
}
