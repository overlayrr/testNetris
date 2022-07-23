package com.example.testnetris.service;


import com.example.testnetris.exchange.CamerasDataUrl;
import com.example.testnetris.exchange.SourceDataUrl;
import com.example.testnetris.exchange.TokenDataUrl;
import com.example.testnetris.exchange.model.Camera;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CameraServiceImpl implements CameraService {
    private final ConcurrentHashMap<Integer, Camera> cameras = new ConcurrentHashMap<>();
    private static final String CAMERA_DATA_URL = "https://www.mocky.io/v2/5c51b9dd3400003252129fb5";
    private final RestTemplate restTemplate;

    public CameraServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "cameras")
    public List<Camera> getAllCameras() {
        creatingConcurrentHashMapWithIdCameras();
        getCamerasDataUrl().forEach(e -> saveSourceData(e.getId(), e.getSourceDataUrl()));
        getCamerasDataUrl().forEach(e -> saveTokenData(e.getId(), e.getTokenDataUrl()));
        return new ArrayList<>(cameras.values());
    }

    private void creatingConcurrentHashMapWithIdCameras(){
        getCamerasDataUrl().forEach(e -> cameras.put(e.getId(), Camera.builder().build()));
    }

    private List<CamerasDataUrl> getCamerasDataUrl() {
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(CAMERA_DATA_URL, Object[].class);
        Object[] objects = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();

        return Arrays.stream(Objects.requireNonNull(objects))
                .map(object -> mapper.convertValue(object, CamerasDataUrl.class))
                .collect(Collectors.toList());
    }

    private void saveSourceData(Integer id, String sourceDataUrl) {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(sourceDataUrl, Object.class);
        Object objects = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        SourceDataUrl convertValue = mapper.convertValue(objects, SourceDataUrl.class);
        cameras.get(id).setId(id);
        cameras.get(id).setUrlType(convertValue.getUrlType());
        cameras.get(id).setVideoUrl(convertValue.getVideoUrl());
    }

    private void saveTokenData(Integer id, String tokenDataUrl) {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(tokenDataUrl, Object.class);
        Object objects = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        TokenDataUrl convertValue = mapper.convertValue(objects, TokenDataUrl.class);
        cameras.get(id).setTtl(convertValue.getTtl());
        cameras.get(id).setValue(convertValue.getValue());
    }
}
