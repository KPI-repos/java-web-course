package com.example.javawebcourse.service;

import com.example.javawebcourse.dto.CosmoCatDto;
import com.example.javawebcourse.featuretoggle.FeatureToggle;
import com.example.javawebcourse.featuretoggle.FeatureToggles;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CosmoCatServiceImpl implements CosmoCatService {

    @Override
    public List<CosmoCatDto> getCosmoCats() {
        return List.of(
                CosmoCatDto.builder()
                        .id(UUID.randomUUID())
                        .name("Galaxy Cat")
                        .build(),
                CosmoCatDto.builder()
                        .id(UUID.randomUUID())
                        .name("Nebula Cat")
                        .build(),
                CosmoCatDto.builder()
                        .id(UUID.randomUUID())
                        .name("Comet Cat")
                        .build()
        );
    }
}
