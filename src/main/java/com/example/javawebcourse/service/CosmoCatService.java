package com.example.javawebcourse.service;

import com.example.javawebcourse.dto.CosmoCatDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CosmoCatService {

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
