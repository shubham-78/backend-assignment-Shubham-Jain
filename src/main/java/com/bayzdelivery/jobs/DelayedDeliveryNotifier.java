
package com.bayzdelivery.jobs;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DelayedDeliveryNotifier {

    @Autowired
    DeliveryService deliveryService;

    @Scheduled(fixedRate = 60000)
    public void notifyDelayedDeliveries() {
        List<Delivery> openDeliveries = deliveryService.findOngoingDeliveries();
        for (Delivery delivery : openDeliveries) {
            System.out.println("[NOTIFY-CS] Delivery ID " + delivery.getId() + " has exceeded 45 minutes.");
        }
    }
}
