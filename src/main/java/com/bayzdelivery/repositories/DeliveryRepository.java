package com.bayzdelivery.repositories;

import com.bayzdelivery.model.Delivery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;

public interface DeliveryRepository extends CrudRepository<Delivery, Long> {

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
            "FROM Delivery d " +
            "WHERE d.deliveryMan.id = :deliveryManId " +
            "AND ((d.startTime <= :end AND d.endTime >= :start))")
    boolean existsOverlappingDelivery(Long deliveryManId, Instant start, Instant end);

    List<Delivery> findAllByStartTimeBetween(Instant start, Instant end);

    @Query(value="""
        SELECT * FROM Delivery
        WHERE end_time IS NULL
        OR (EXTRACT(EPOCH FROM end_time) - EXTRACT(EPOCH FROM start_time)) > 2700
        """, nativeQuery = true)
    List<Delivery> findOngoingDeliveries();
}