package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.domain.model.Capacity;
import com.pragma.bootcamp.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacityEntityMapper {

    Capacity toModel(CapacityEntity capacityEntity);
    List<Capacity> toModelList(List<CapacityEntity> capacityEntities);
    @Mapping(target = "technologies", qualifiedByName = "mapTechnologies")
    CapacityEntity toEntity(Capacity capacity);

    @Named("mapTechnologies")
    default List<TechnologyEntity> mapTechnologies(List<Technology> technologies) {
        if (technologies == null) {
            return new ArrayList<>();
        }
        return technologies.stream()
                .map(this::toEntity)
                .toList();
    }

    @Mapping(target = "capacities", ignore = true)
    @Mapping(target = "description", ignore = true)
    TechnologyEntity toEntity(Technology technology);
}
