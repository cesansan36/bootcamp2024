package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.domain.model.Bootcamp;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampEntityMapper {

    Bootcamp toModel(BootcampEntity bootcampEntity);
    List<Bootcamp> toModelList(List<BootcampEntity> bootcampEntities);

    @Mapping(target = "capacities", qualifiedByName = "mapCapacities")
    BootcampEntity toEntity(Bootcamp bootcamp);

    @Named("mapCapacities")
    default List<CapacityEntity> mapCapacities(List<Capacity> capacities) {
        if (capacities == null) {
            return new ArrayList<>();
        }
        return capacities.stream()
                .map(this::toEntity)
                .toList();
    }

    @Mapping(target = "bootcamps", ignore = true)
    @Mapping(target = "description", ignore = true)
    CapacityEntity toEntity(Capacity capacity);
}
