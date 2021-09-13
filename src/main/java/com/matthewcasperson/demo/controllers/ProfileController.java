// src/main/java/com/matthewcasperson/demo/controllers/ProfileController.java

package com.matthewcasperson.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    private OAuth2AuthorizedClientService clientService;

    @Autowired
    public ProfileController(OAuth2AuthorizedClientService clientService) {
        this.clientService = clientService;
    }

    // https://spring.io/blog/2021/01/13/the-latest-on-azure-active-directory-integration
    @GetMapping("/profile")
    public ModelAndView profile(@AuthenticationPrincipal OidcUser principal) {
        try {
            ModelAndView mav = new ModelAndView("profile");
            mav.addObject("tokenAttributes", getObjectAsJSON(principal.getAttributes()));
            return mav;
        } catch (Exception ex) {
            ModelAndView mav = new ModelAndView("profile");
            mav.addObject("jwt", ex.toString());
            return mav;
        }
    }

    private String getObjectAsJSON(Object attributes) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(attributes);
        } catch (JsonProcessingException e) {
            return "{\"message\":\"" + e + "\"}";
        }
    }
}