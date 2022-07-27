package com.example.testnetris.service;

import com.example.testnetris.exchange.model.Camera;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

public interface CameraService {

    Collection<Camera> getAllCameras() throws ExecutionException, InterruptedException;

}
