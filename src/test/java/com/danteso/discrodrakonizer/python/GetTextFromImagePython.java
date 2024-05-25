package com.danteso.discrodrakonizer.python;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class GetTextFromImagePython {


    @Test
    public void sendFile() throws IOException {
        String pythonEndpointUrl = "http://localhost:5000/process_image";
        String fileName = "img_2.png";
        File file = new File(fileName);
        byte[] imageBytes = Files.readAllBytes(Paths.get(fileName));
        RestTemplate restTemplate = new RestTemplate();
        //System.out.println(file);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return "img_2.png";
            }
        });
        body.add("ilka", "test");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        //System.out.println(body);
        ResponseEntity<String> response = restTemplate.postForEntity(pythonEndpointUrl, requestEntity, String.class);
        System.out.println(response.getBody());
    }
}
