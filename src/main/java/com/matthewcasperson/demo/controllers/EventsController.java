// src/main/java/com/matthewcasperson/demo/controllers/EventController.java

package com.matthewcasperson.demo.controllers;

import com.matthewcasperson.demo.model.GraphEvent;
import com.matthewcasperson.demo.model.GraphValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Controller
public class EventsController {

    @Autowired
    private WebClient webClient;

    @GetMapping("/events")
    public ModelAndView profile(
            @RegisteredOAuth2AuthorizedClient("calendar-api") OAuth2AuthorizedClient client) {

        List<GraphEvent> events = getEvents(client).value();
        ModelAndView mav = new ModelAndView("events");
        mav.addObject("events", events);
        return mav;
    }

    private GraphValues getEvents(OAuth2AuthorizedClient client) {
        try {
            if (null != client) {
                System.out.println("\n" + client.getAccessToken().getTokenValue() + "\n");

                return webClient
                        .get()
                        .uri("http://localhost:8081/events")
                        .attributes(oauth2AuthorizedClient(client))
                        .retrieve()
                        .bodyToMono(GraphValues.class)
                        .block();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return new GraphValues(List.of());
    }
}