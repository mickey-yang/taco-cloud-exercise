package com.tacos.repository;

import com.tacos.domain.Taco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepositroy extends JpaRepository<Taco, Long> {
}