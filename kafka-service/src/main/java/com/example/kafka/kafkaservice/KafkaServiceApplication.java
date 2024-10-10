package com.example.kafka.kafkaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.example.kafka.kafkaservice.bindings.MyStreamBindings;
import com.github.lalyos.jfiglet.FigletFont;


@SpringBootApplication
@EnableBinding(MyStreamBindings.class)
public class KafkaServiceApplication {

    private static final String RESET = "\033[0m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String RED = "\033[0;31m";
    
    public static void main(String[] args) {
    	long startTime = System.currentTimeMillis();
        SpringApplication.run(KafkaServiceApplication.class, args);
        long endTime = System.currentTimeMillis();
        long uptime = endTime - startTime;
        String text = "Kafka's  Ready  to  Roll";
        try {
            String asciiArt = FigletFont.convertOneLine(text);
            System.out.println(asciiArt);
            System.out.println(GREEN + "Service started in " + YELLOW + uptime + " ms" + RESET);
        } catch (Exception e) {
            System.err.println("Error generating ASCII art: " + e.getMessage());
        }
    }
}
