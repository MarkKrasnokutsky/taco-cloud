package com.mark.taco_cloud.repository;

import com.mark.taco_cloud.domain.dto.TacoOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, Long> {



}
