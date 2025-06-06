
package com.bayzdelivery.controller;

import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    DeliveryService deliveryService;

    @GetMapping("/top")
    public ResponseEntity<List<Map<String, Object>>> getTopDeliveryMen(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
        return ResponseEntity.ok(deliveryService.getTopDeliveryMenWithAverageCommission(start, end));
    }

    @GetMapping("/{delivery-id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable("delivery-id") Long deliveryId) {
        Delivery delivery = deliveryService.findById(deliveryId);
        return (delivery != null) ? ResponseEntity.ok(delivery) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Delivery> createNewDelivery(@RequestBody Delivery delivery) {
        return ResponseEntity.ok(deliveryService.save(delivery));
    }

    @DeleteMapping("/{delivery-id}")
    public ResponseEntity<Void> deleteDeliveryById(@PathVariable("delivery-id") Long deliveryId) {
        Delivery delivery = deliveryService.findById(deliveryId);
        if(delivery==null){
            return ResponseEntity.notFound().build();
        }
        deliveryService.delete(deliveryId);
        return ResponseEntity.noContent().build();
    }
}
