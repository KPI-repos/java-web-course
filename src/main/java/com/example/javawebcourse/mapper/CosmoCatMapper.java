package com.example.javawebcourse.mapper;

import com.example.javawebcourse.domain.CosmoCat;
import com.example.javawebcourse.dto.CosmoCatDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CosmoCatMapper {
    CosmoCatDto cosmoCatToDto(CosmoCat cosmoCat);
    CosmoCat dtoToCosmoCat(CosmoCatDto dto);
}
