package com.pragma.bootcamp.testdata;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.domain.model.Technology;

import java.util.ArrayList;
import java.util.List;

public class TestDataDriven {
    private TestDataDriven() {throw new IllegalStateException("Utility class");}

    public enum Element {
        TECHNOLOGY,
        CAPACITY,
        BOOTCAMP
    }
    public static final String ENTITY_NAME = "%1$s name %2$s";
    public static final String ENTITY_DESCRIPTION = "%1$s name %2$s";

    public static String getValidName(Long id, Element element) {
        return String.format(ENTITY_NAME, element, id);
    }
    public static String getValidDescription(Long id, Element element) {
        return String.format(ENTITY_DESCRIPTION, element, id);
    }

    public static TechnologyEntity getTechnologyEntity(Long id){
        TechnologyEntity entity = new TechnologyEntity();
        entity.setId(id);
        entity.setName(getValidName(id, Element.TECHNOLOGY));
        entity.setName(getValidDescription(id, Element.TECHNOLOGY));
        return entity;
    }
    public static List<TechnologyEntity> getListOfTechnologyEntity(int num){
        List<TechnologyEntity> entities = new ArrayList<>();
        for (long i = 1L ; i <= num ; i++) {
            entities.add(getTechnologyEntity(i));
        }
        return entities;
    }

    public static CapacityEntity getCapacityEntity(Long id){
        CapacityEntity entity = new CapacityEntity();
        entity.setId(id);
        entity.setName(getValidName(id, Element.CAPACITY));
        entity.setName(getValidDescription(id, Element.CAPACITY));
        return entity;
    }
    public static List<CapacityEntity> getListOfCapacityEntity(int num){
        List<CapacityEntity> entities = new ArrayList<>();
        for (long i = 1L ; i <= num ; i++) {
            entities.add(getCapacityEntity(i));
        }
        return entities;
    }

    public static BootcampEntity getBootcampEntity(Long id){
        BootcampEntity entity = new BootcampEntity();
        entity.setId(id);
        entity.setName(getValidName(id, Element.BOOTCAMP));
        entity.setName(getValidDescription(id, Element.BOOTCAMP));
        return entity;
    }
    public static List<BootcampEntity> getListOfBootcampEntity(int num){
        List<BootcampEntity> entities = new ArrayList<>();
        for (long i = 1L ; i <= num ; i++) {
            entities.add(getBootcampEntity(i));
        }
        return entities;
    }
}
