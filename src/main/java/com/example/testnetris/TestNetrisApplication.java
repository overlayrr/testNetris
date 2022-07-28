package com.example.testnetris;

import com.example.testnetris.exchange.model.Camera;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@EnableAsync(proxyTargetClass = true)
@EnableScheduling
@SpringBootApplication
public class TestNetrisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestNetrisApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public ConcurrentMap<Integer, Camera> getDataCameras(){
        return new ConcurrentHashMap<>();
    }
}
