package com.example.javawebcourse.mapper;

import com.example.javawebcourse.domain.Category;
import com.example.javawebcourse.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryDtoMapper {

    CategoryDto categoryToDto(Category category);
    Category dtoToCategory(CategoryDto dto);
}
