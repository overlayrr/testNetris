package com.example.testnetris.service;

import com.example.testnetris.exchange.CamerasDataUrl;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CameraDataService {
    void saveSourceData(Integer id, String sourceDataUrl);

    CompletableFuture<List<CamerasDataUrl>> getCamerasDataUrl();

    void saveTokenData(Integer id, String tokenDataUrl);
}
