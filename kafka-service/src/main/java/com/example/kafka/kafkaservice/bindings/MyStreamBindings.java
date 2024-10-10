package com.example.kafka.kafkaservice.bindings;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MyStreamBindings {

    String INPUT = "input";

    @Input(INPUT)
    SubscribableChannel input();
}

