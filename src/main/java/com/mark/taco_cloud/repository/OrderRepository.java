package com.mark.taco_cloud.repository;

import com.mark.taco_cloud.domain.dto.TacoOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<TacoOrder, Long> {

}
