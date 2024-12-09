package com.example.javawebcourse.api.web;

import com.example.javawebcourse.web.CosmoCatController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.example.javawebcourse.api.annotation.DisabledFeatureToggle;
import com.example.javawebcourse.api.annotation.EnabledFeatureToggle;
import com.example.javawebcourse.featuretoggle.FeatureToggles;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CosmoCatController.class)
class CosmoCatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldGet404FeatureDisabled() throws Exception {
        mockMvc.perform(get("/api/cosmo-cats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @EnabledFeatureToggle(FeatureToggles.COSMO_CATS)
    void shouldGet200FeatureEnabled() throws Exception {
        mockMvc.perform(get("/api/cosmo-cats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}