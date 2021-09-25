// src/main/java/com/matthewcasperson/demo/controllers/UploadController.java

package com.matthewcasperson.demo.controllers;

import com.matthewcasperson.demo.model.GraphValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Controller
public class UploadController {

    @Autowired
    private WebClient webClient;

    @GetMapping("/upload")
    public String upload() {
        return "upload";
    }

    @PutMapping("/upload/{fileName}")
    public String uploadFile(
            @PathVariable("fileName") String fileName,
            @RequestBody String body,
            @RegisteredOAuth2AuthorizedClient("azure-api") OAuth2AuthorizedClient client) {

        saveFile(client, fileName, body);
        return "upload";
    }

    private void saveFile(OAuth2AuthorizedClient client, String fileName, String body) {
        try {
            if (null != client) {
                webClient
                        .put()
                        .uri("http://localhost:8082/upload/" + fileName)
                        .body(BodyInserters.fromValue(body))
                        .attributes(oauth2AuthorizedClient(client))
                        .retrieve()
                        .bodyToMono(GraphValues.class)
                        .block();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}