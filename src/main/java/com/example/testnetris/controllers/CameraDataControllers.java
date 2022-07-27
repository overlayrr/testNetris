package com.example.testnetris.controllers;

import com.example.testnetris.exchange.model.Camera;
import com.example.testnetris.service.CameraService;
import com.example.testnetris.service.CameraServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Tag(name = "CameraData")
@RestController
@RequestMapping("/v1/cameras")
public class CameraDataControllers {
    private final CameraService camerasService;

    public CameraDataControllers(CameraServiceImpl camerasService) {
        this.camerasService = camerasService;
    }

    @SneakyThrows
    @GetMapping
    public Collection<Camera> getAllCamerasData(){
       return camerasService.getAllCameras();
    }
}
