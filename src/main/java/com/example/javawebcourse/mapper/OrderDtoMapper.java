package com.example.javawebcourse.mapper;

import com.example.javawebcourse.domain.order.Order;
import com.example.javawebcourse.domain.order.OrderEntry;
import com.example.javawebcourse.domain.Product;
import com.example.javawebcourse.dto.order.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class OrderDtoMapper {


    @Mapping(target = "entryIds", source = "entries")
    public abstract OrderDto orderToDto(Order order);

    @Mapping(target = "entries", source = "entryIds")
    public abstract Order dtoToOrder(OrderDto dto);

    protected List<UUID> entriesToEntryIds(List<OrderEntry> entries) {
        return entries.stream()
                .map(entry -> entry.getProduct().getId())
                .collect(Collectors.toList());
    }

    protected List<OrderEntry> entryIdsToEntries(List<UUID> entryIds) {
        return entryIds.stream()
                .map(id -> createOrderEntryFromProductId(id))
                .collect(Collectors.toList());
    }

    protected OrderEntry createOrderEntryFromProductId(UUID id) {
        return OrderEntry.builder()
                .product(Product.builder()
                        .id(id)
                        .build())
                .quantity(0)
                .build();
    }
}
