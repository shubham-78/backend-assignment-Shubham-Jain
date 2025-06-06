
package com.bayzdelivery.service;

import com.bayzdelivery.model.Delivery;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface DeliveryService {
    Delivery save(Delivery delivery);
    Delivery findById(Long id);
    List<Map<String, Object>> getTopDeliveryMenWithAverageCommission(Instant start, Instant end);
    List<Delivery> findOngoingDeliveries();
    void delete(Long deliveryId);
}
