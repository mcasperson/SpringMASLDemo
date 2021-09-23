// src/main/java/com/matthewcasperson/demo/controllers/EventController.java

package com.matthewcasperson.demo.controllers;

import com.matthewcasperson.demo.model.GraphEvent;
import com.matthewcasperson.demo.model.GraphValues;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EventsController {

    @GetMapping("/events")
    public ModelAndView profile(
            @RegisteredOAuth2AuthorizedClient("calendar-api") OAuth2AuthorizedClient client) {

        List<GraphEvent> events = getEvents(client).value();
        ModelAndView mav = new ModelAndView("events");
        mav.addObject("events", events);
        return mav;
    }

    private GraphValues getEvents(OAuth2AuthorizedClient graph) {
        try {
            if (null != graph) {
                System.out.println("\n" + graph.getAccessToken().getTokenValue() + "\n");

                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", "Bearer " + graph.getAccessToken().getTokenValue());
                HttpEntity<String> entity = new HttpEntity<>(null, headers);

                return restTemplate.exchange(
                        "http://localhost:8081/events",
                        HttpMethod.GET,
                        entity,
                        GraphValues.class)
                        .getBody();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return new GraphValues(List.of());
    }
}