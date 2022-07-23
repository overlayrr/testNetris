package com.example.testnetris.exchange;

import lombok.Data;

import java.io.Serializable;


@Data
public class SourceDataUrl implements Serializable {

    private String urlType;
    private String videoUrl;
}
