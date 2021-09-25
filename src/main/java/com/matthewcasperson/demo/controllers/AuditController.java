// src/main/java/com/matthewcasperson/demo/controllers/AuditController.java

package com.matthewcasperson.demo.controllers;

import com.matthewcasperson.demo.model.Audit;
import com.matthewcasperson.demo.model.GraphEvent;
import com.matthewcasperson.demo.model.GraphValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Controller
public class AuditController {

    @Autowired
    private WebClient webClient;

    @GetMapping("/audit")
    public ModelAndView events(
            @RegisteredOAuth2AuthorizedClient("azure-api") OAuth2AuthorizedClient client) {

        List<Audit> events = getAudit(client);
        ModelAndView mav = new ModelAndView("audit");
        mav.addObject("audit", events);
        return mav;
    }

    private List<Audit> getAudit(OAuth2AuthorizedClient client) {
        try {
            if (null != client) {
                System.out.println("\n" + client.getAccessToken().getTokenValue() + "\n");

                return webClient
                        .get()
                        .uri("http://localhost:8082/audit")
                        .attributes(oauth2AuthorizedClient(client))
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<List<Audit>>() {})
                        .block();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return new ArrayList<>();
    }
}