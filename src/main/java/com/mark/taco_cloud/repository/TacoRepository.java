package com.mark.taco_cloud.repository;

import com.mark.taco_cloud.domain.dto.Taco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}
