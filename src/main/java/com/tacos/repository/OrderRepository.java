package com.tacos.repository;

import com.tacos.domain.TacoOrder;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {

    List<TacoOrder> findByDeliveryPostalCode(String postalCode);

    // DSL "PostalCode" should mean find by attribute ".postalCode"
    List<TacoOrder> findByDeliveryPostalCodeAndPlacedAtBetween(String postalCode, LocalDate startDate, LocalDate endDate);

    List<TacoOrder> findByDeliveryNameAndDeliveryCityAllIgnoringCase(String deliveryName, String deliveryCity);

    List<TacoOrder> findByDeliveryCityOrderByDeliveryName(String city);

}
