package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.domain.model.Capability;
import com.pragma.bootcamp.domain.model.Technology;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapabilityEntityMapper {

    Capability toModel(CapabilityEntity capabilityEntity);
    List<Capability> toModelList(List<CapabilityEntity> capabilityEntities);
    @Mapping(target = "technologies", qualifiedByName = "mapTechnologies")
    @Mapping(target = "bootcamps", ignore = true)
    CapabilityEntity toEntity(Capability capability);

    @Named("mapTechnologies")
    default List<TechnologyEntity> mapTechnologies(List<Technology> technologies) {
        if (technologies == null) {
            return new ArrayList<>();
        }
        return technologies.stream()
                .map(this::toEntity)
                .toList();
    }

    @Mapping(target = "capabilities", ignore = true)
    @Mapping(target = "description", ignore = true)
    TechnologyEntity toEntity(Technology technology);
}
