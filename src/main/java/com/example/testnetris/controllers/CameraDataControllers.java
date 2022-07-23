package com.example.testnetris.controllers;

import com.example.testnetris.exchange.model.Camera;
import com.example.testnetris.service.CameraService;
import com.example.testnetris.service.CameraServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "CameraData")
@RestController
@RequestMapping("/v1/cameras")
public class CameraDataControllers {

    private final CameraService camerasService;

    public CameraDataControllers(CameraServiceImpl camerasService) {
        this.camerasService = camerasService;
    }

    @GetMapping
    public List<Camera> getAllCamerasData(){
       return camerasService.getAllCameras();
    }
}