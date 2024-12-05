package com.example.javawebcourse.web.mapper;

import com.example.javawebcourse.domain.order.Order;
import com.example.javawebcourse.domain.order.OrderEntry;
import com.example.javawebcourse.domain.product.Product;
import com.example.javawebcourse.dto.order.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderDtoMapper {


    @Mapping(target = "entryIds", source = "entries")
    OrderDto orderToDto(Order order);

    @Mapping(target = "entries", source = "entryIds")
    Order dtoToOrder(OrderDto dto);

    default List<UUID> entriesToEntryIds(List<OrderEntry> entries) {
        return entries.stream()
                .map(entry -> entry.getProduct().getId())
                .collect(Collectors.toList());
    }

    default List<OrderEntry> entryIdsToEntries(List<UUID> entryIds) {
        return entryIds.stream()
                .map(id -> createOrderEntryFromProductId(id))
                .collect(Collectors.toList());
    }

    default OrderEntry createOrderEntryFromProductId(UUID id) {
        return OrderEntry.builder()
                .product(Product.builder()
                        .id(id)
                        .build())
                .quantity(0)
                .build();
    }
}
