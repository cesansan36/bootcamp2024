package com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampVersionEntity;
import com.pragma.bootcamp.domain.model.BootcampVersion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampVersionEntityMapper {

    BootcampVersion toModel(BootcampVersionEntity bootcampVersionEntity);
    List<BootcampVersion> toModelList(List<BootcampVersionEntity> bootcampVersions);
    BootcampVersionEntity toEntity(BootcampVersion bootcampVersion);
}
