package com.example.javawebcourse.config.security.users;

import org.mapstruct.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public abstract class SecurityUserMapper {

    public abstract GetSecurityUserDTO toDTO(SecurityUser user);

    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract SecurityUser toEntity(PostSecurityUserDTO userDto);

    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void mapNonNullIntoEntity(PostSecurityUserDTO dto, @MappingTarget SecurityUser base);

    protected ZonedDateTime mapInstantToZonedDateTime(Instant instant) {
        return instant == null ? null : instant.atZone(ZoneId.systemDefault());
    }
}
