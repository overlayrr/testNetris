package com.example.testnetris.exchange.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Camera {

    private Integer id;
    private String urlType;
    private String videoUrl;
    private String value;
    private Integer ttl;

}
