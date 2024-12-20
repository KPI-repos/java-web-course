package com.example.javawebcourse.service;

import com.example.javawebcourse.dto.CosmoCatDto;
import com.example.javawebcourse.featuretoggle.FeatureToggle;
import com.example.javawebcourse.featuretoggle.FeatureToggles;

import java.util.List;

public interface CosmoCatService {
    @FeatureToggle(FeatureToggles.COSMO_CATS)
    List<CosmoCatDto> getCosmoCats();
}
