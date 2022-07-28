package com.example.testnetris.service.impl;

import com.example.testnetris.exchange.CamerasDataUrl;
import com.example.testnetris.exchange.SourceData;
import com.example.testnetris.exchange.TokenData;
import com.example.testnetris.exchange.model.Camera;
import com.example.testnetris.service.CameraDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class CameraDataServiceImpl implements CameraDataService {
    private final RestTemplate restTemplate;
    private static final String CAMERA_DATA_URL = "https://www.mocky.io/v2/5c51b9dd3400003252129fb5";

    private final ConcurrentMap<Integer, Camera> cameras;

    public CameraDataServiceImpl(RestTemplate restTemplate, ConcurrentMap<Integer, Camera> cameras) {
        this.restTemplate = restTemplate;
        this.cameras = cameras;
    }
    @Async
    public void saveSourceData(Integer id, String sourceDataUrl) {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(sourceDataUrl, Object.class);
        Object objects = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        SourceData convertValue = mapper.convertValue(objects, SourceData.class);
        cameras.get(id).setId(id);
        cameras.get(id).setUrlType(convertValue.getUrlType());
        cameras.get(id).setVideoUrl(convertValue.getVideoUrl());
    }

    @Async
    public void saveTokenData(Integer id, String tokenDataUrl) {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(tokenDataUrl, Object.class);
        Object objects = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        TokenData convertValue = mapper.convertValue(objects, TokenData.class);
        cameras.get(id).setTtl(convertValue.getTtl());
        cameras.get(id).setValue(convertValue.getValue());
    }

    @Async
    public CompletableFuture<List<CamerasDataUrl>> getCamerasDataUrl() {
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(CAMERA_DATA_URL, Object[].class);
        Object[] objects = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();

        return CompletableFuture.completedFuture(Arrays.stream(Objects.requireNonNull(objects)).parallel()
                .map(object -> mapper.convertValue(object, com.example.testnetris.exchange.CamerasDataUrl.class))
                .collect(Collectors.toList()));
    }
}
