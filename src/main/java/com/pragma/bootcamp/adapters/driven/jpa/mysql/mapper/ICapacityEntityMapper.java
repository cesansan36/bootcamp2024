package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.domain.model.Capacity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacityEntityMapper {

    Capacity toModel(CapacityEntity capacityEntity);
    List<Capacity> toModelList(List<CapacityEntity> capacityEntities);
    CapacityEntity toEntity(Capacity capacity);
}
