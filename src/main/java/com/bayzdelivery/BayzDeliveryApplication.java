package com.bayzdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BayzDeliveryApplication {
  public static void main(String[] args) {
    SpringApplication.run(BayzDeliveryApplication.class, args);
  }
}
