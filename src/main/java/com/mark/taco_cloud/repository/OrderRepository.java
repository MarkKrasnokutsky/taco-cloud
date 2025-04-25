package com.mark.taco_cloud.repository;

import com.mark.taco_cloud.domain.dto.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    TacoOrder save(TacoOrder order);

}
