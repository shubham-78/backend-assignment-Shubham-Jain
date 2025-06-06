package com.bayzdelivery.service;

import com.bayzdelivery.exceptions.DeliveryConflictException;
import com.bayzdelivery.exceptions.InvalidRoleException;
import com.bayzdelivery.model.Delivery;
import com.bayzdelivery.model.Person;
import com.bayzdelivery.repositories.DeliveryRepository;
import com.bayzdelivery.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    PersonRepository personRepository;

    @Override
    public Delivery save(Delivery delivery) {
        Person deliveryMan = delivery.getDeliveryMan();
        Person customer = delivery.getCustomer();

        // Fetch full person objects from DB
        Person deliveryManEntity = personRepository.findById(deliveryMan.getId())
                .orElseThrow(() -> new IllegalArgumentException("Delivery man not found"));

        Person customerEntity = personRepository.findById(customer.getId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        // ✅ Validate roles
        if (!deliveryManEntity.getPersonType().name().equals("DELIVERY")) {
            throw new InvalidRoleException("Provided deliveryMan ID does not belong to a CUSTOMER user.");
        }

        if (!customerEntity.getPersonType().name().equals("CUSTOMER")) {
            throw new InvalidRoleException("Provided customer ID does not belong to a DELIVERY user.");
        }

        // Update references with fully-loaded and validated objects
        delivery.setDeliveryMan(deliveryManEntity);
        delivery.setCustomer(customerEntity);

        // Check for overlapping deliveries
        boolean overlapping = deliveryRepository.existsOverlappingDelivery(
                delivery.getDeliveryMan().getId(),
                delivery.getStartTime(),
                delivery.getEndTime()
        );

        if (overlapping) {
            throw new DeliveryConflictException("Delivery man has another delivery at this time.");
        }

        // Calculate commission
        double commission = delivery.getPrice() * 0.05 + delivery.getDistance() * 0.5;
        delivery.setComission((long) commission);

        return deliveryRepository.save(delivery);
    }

    @Override
    public Delivery findById(Long id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Map<String, Object>> getTopDeliveryMenWithAverageCommission(Instant start, Instant end) {
        List<Delivery> deliveries = deliveryRepository.findAllByStartTimeBetween(start, end);

        Map<Long, List<Delivery>> grouped = deliveries.stream()
                .collect(Collectors.groupingBy(d -> d.getDeliveryMan().getId()));

        return grouped.entrySet().stream()
                .map(e -> {
                    double total = e.getValue().stream()
                            .mapToDouble(d -> d.getPrice() * 0.05 + d.getDistance() * 0.5)
                            .sum();
                    double avg = total / e.getValue().size();

                    Map<String, Object> result = new HashMap<>();
                    result.put("deliveryManId", e.getKey());
                    result.put("totalCommission", total);
                    result.put("averageCommission", avg);
                    return result;
                })
                .sorted((a, b) -> Double.compare((double) b.get("totalCommission"), (double) a.get("totalCommission")))
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public List<Delivery> findOngoingDeliveries() {
        return deliveryRepository.findOngoingDeliveries();
    }

    public void delete(Long deliveryId){
        deliveryRepository.deleteById(deliveryId);
    }
}