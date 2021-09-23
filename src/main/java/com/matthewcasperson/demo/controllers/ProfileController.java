// src/main/java/com/matthewcasperson/demo/controllers/ProfileController.java

package com.matthewcasperson.demo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public ModelAndView profile(
            @AuthenticationPrincipal OidcUser principal) {

        ModelAndView mav = new ModelAndView("profile");
        mav.addObject("tokenAttributes", getObjectAsJSON(principal.getAttributes()));
        return mav;
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