package com.example.javawebcourse.web.mapper;

import java.util.List;
import com.example.javawebcourse.domain.product.Product;
import com.example.javawebcourse.dto.product.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ProductDtoMapper {

    List<ProductDto> toProductDto(List<Product> products);

    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toProductDto(Product product);

    @Mapping(target = "category.id", source = "categoryId")
    Product toProduct(ProductDto productDto);
}
