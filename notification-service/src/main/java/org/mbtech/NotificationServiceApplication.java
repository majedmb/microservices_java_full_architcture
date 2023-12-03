package org.mbtech;

import lombok.extern.slf4j.Slf4j;
import org.mbtech.event.PlaceOrderEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }


    @KafkaListener(topics = "notificationOrderTopic")
    public void notificationOrderTopic(PlaceOrderEvent placeOrderEvent){
        // send out some mail as notification....
        log.info("Notification received for order - {}", placeOrderEvent.getOrderNumber());
    }

}