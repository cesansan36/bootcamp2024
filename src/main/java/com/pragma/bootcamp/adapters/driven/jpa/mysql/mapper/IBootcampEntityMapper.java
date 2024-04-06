package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampEntityMapper {

    Bootcamp toModel(BootcampEntity bootcampEntity);
    List<Bootcamp> toModelList(List<BootcampEntity> bootcampEntities);

    @Mapping(target = "capabilities", qualifiedByName = "mapCapabilities")
    BootcampEntity toEntity(Bootcamp bootcamp);

    @Named("mapCapabilities")
    default List<CapabilityEntity> mapCapabilities(List<Capability> capabilities) {
        if (capabilities == null) {
            return new ArrayList<>();
        }
        return capabilities.stream()
                .map(this::toEntity)
                .toList();
    }

    @Mapping(target = "bootcamps", ignore = true)
    @Mapping(target = "description", ignore = true)
    CapabilityEntity toEntity(Capability capability);
}
