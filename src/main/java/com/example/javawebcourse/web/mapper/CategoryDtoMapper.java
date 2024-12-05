package com.example.javawebcourse.web.mapper;

import com.example.javawebcourse.domain.category.Category;
import com.example.javawebcourse.dto.category.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CategoryDtoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDto categoryToDto(Category category);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Category dtoToCategory(CategoryDto dto);
}
