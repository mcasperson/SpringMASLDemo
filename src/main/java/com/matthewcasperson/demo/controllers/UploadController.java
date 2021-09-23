// src/main/java/com/matthewcasperson/demo/controllers/EventController.java

package com.matthewcasperson.demo.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;

@Controller
public class UploadController {

    @GetMapping("/upload")
    public String upload(@RegisteredOAuth2AuthorizedClient("azure-api") OAuth2AuthorizedClient client) {
        System.out.println("\n" + client.getAccessToken().getTokenValue() + "\n");
        return "upload";
    }

    @PutMapping("/upload/{fileName}")
    public String uploadFile(
            @PathParam("fileName") String fileName,
            @RequestBody String body,
            @RegisteredOAuth2AuthorizedClient("azure-api") OAuth2AuthorizedClient client) {

        saveFile(client, fileName, body);
        return "upload";
    }

    private void saveFile(OAuth2AuthorizedClient client, String fileName, String body) {
        try {
            if (null != client) {
                System.out.println("\n" + client.getAccessToken().getTokenValue() + "\n");

                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.TEXT_PLAIN);
                headers.set("Authorization", "Bearer " + client.getAccessToken().getTokenValue());
                HttpEntity<String> entity = new HttpEntity<>(body, headers);

                restTemplate.exchange(
                        "http://localhost:8082/upload/" + fileName,
                        HttpMethod.PUT,
                        entity,
                        String.class);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}