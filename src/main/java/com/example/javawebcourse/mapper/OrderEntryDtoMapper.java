package com.example.javawebcourse.mapper;

import com.example.javawebcourse.domain.order.OrderEntry;
import com.example.javawebcourse.dto.order.OrderEntryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderEntryDtoMapper {

    @Mapping(target = "productId", source = "product.id")
    OrderEntryDto orderEntryToDto(OrderEntry orderEntry);

    @Mapping(target = "product.id", source = "productId")
    OrderEntry dtoToOrderEntry(OrderEntryDto orderEntryDto);

}
