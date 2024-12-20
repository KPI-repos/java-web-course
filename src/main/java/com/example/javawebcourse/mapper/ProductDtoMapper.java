package com.example.javawebcourse.mapper;

import com.example.javawebcourse.domain.Product;
import com.example.javawebcourse.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toProductDto(Product product);

    @Mapping(target = "category.id", source = "categoryId")
    Product toProduct(ProductDto productDto);
}
