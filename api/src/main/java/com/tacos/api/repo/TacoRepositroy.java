package com.tacos.api.repo;

import com.tacos.api.domain.Taco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepositroy extends JpaRepository<Taco, Long> {
}
