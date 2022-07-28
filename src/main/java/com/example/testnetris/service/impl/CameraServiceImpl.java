package com.example.testnetris.service.impl;


import com.example.testnetris.exceptions.CameraDataException;
import com.example.testnetris.exchange.model.Camera;
import com.example.testnetris.service.CameraDataService;
import com.example.testnetris.service.CameraService;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

@Service
public class CameraServiceImpl implements CameraService {
    private final ConcurrentMap<Integer, Camera> cameras;
    private final CameraDataService cameraDataService;


    public CameraServiceImpl(CameraDataServiceImpl saveSourceData, ConcurrentMap<Integer, Camera> cameras) {
        this.cameraDataService = saveSourceData;
        this.cameras = cameras;
    }

    @SneakyThrows
    @PostConstruct
    private void creatingConcurrentHashMapWithIdCameras() {
        cameraDataService.getCamerasDataUrl().get().forEach(e -> cameras.put(e.getId(), Camera.builder().build()));
        getAllCameras();
    }

    @Async
    public CompletableFuture<Collection<Camera>> getAllCameras() {
        try {
            return CompletableFuture.completedFuture(mergingOfCameraData().get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CameraDataException("Поток прервался " + e.getMessage());
        } catch (ExecutionException e) {
            throw new CameraDataException("Исключение при попытке получить результат задачи " + e.getMessage());
        }
    }

    public CompletableFuture<Collection<Camera>> mergingOfCameraData() throws ExecutionException, InterruptedException {
        cameraDataService.getCamerasDataUrl().get()
                .parallelStream()
                .forEach(e -> cameraDataService.saveSourceData(e.getId(), e.getSourceDataUrl()));

        cameraDataService.getCamerasDataUrl().get()
                .parallelStream()
                .forEach(e -> cameraDataService.saveTokenData(e.getId(), e.getTokenDataUrl()));
        return CompletableFuture.completedFuture(cameras.values());
    }


}
