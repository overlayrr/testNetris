package com.example.testnetris.service;


import com.example.testnetris.exchange.CamerasDataUrl;
import com.example.testnetris.exchange.SourceData;
import com.example.testnetris.exchange.TokenData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CamerasServiceImplTest {

    @Autowired
    private TestRestTemplate restTemplate;
    private static final String CAMERA_DATA_URL = "https://www.mocky.io/v2/5c51b9dd3400003252129fb5";
    private static final String CAMERA_DATA_SOURCE_INDEX1 = "http://www.mocky.io/v2/5c51b230340000094f129f5d";
    private static final String CAMERA_DATA_TOKEN_INDEX1 = "http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s";


    @Test
    void getCamerasDataUrl() {
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(CAMERA_DATA_URL, Object[].class);
        Object[] objects = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        List<CamerasDataUrl> collect = Arrays.stream(Objects.requireNonNull(objects))
                .map(object -> mapper.convertValue(object, CamerasDataUrl.class))
                .collect(Collectors.toList());

        assertThat(collect, hasSize(4));
        assertThat(collect.get(0).getId(), is(1));
        assertThat(collect.get(0).getSourceDataUrl(), is("http://www.mocky.io/v2/5c51b230340000094f129f5d"));
        assertThat(collect.get(0).getTokenDataUrl(), is("http://www.mocky.io/v2/5c51b5b6340000554e129f7b?mocky-delay=1s"));
    }

    @Test
    void saveSourceData() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(CAMERA_DATA_SOURCE_INDEX1, Object.class);
        Object objects = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        SourceData convertValue = mapper.convertValue(objects, SourceData.class);

        assertThat(convertValue.getUrlType(), is("LIVE"));
        assertThat(convertValue.getVideoUrl(), is("rtsp://127.0.0.1/1"));
    }

    @Test
    void saveTokenData() {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(CAMERA_DATA_TOKEN_INDEX1, Object.class);
        Object objects = responseEntity.getBody();

        ObjectMapper mapper = new ObjectMapper();
        TokenData convertValue = mapper.convertValue(objects, TokenData.class);
        assertThat(convertValue.getValue(), is("fa4b588e-249b-11e9-ab14-d663bd873d93"));
        assertThat(convertValue.getTtl(), is(120));
    }
}