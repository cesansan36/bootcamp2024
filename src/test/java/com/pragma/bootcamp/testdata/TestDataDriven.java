package com.pragma.bootcamp.testdata;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;

import java.util.ArrayList;
import java.util.List;

public class TestDataDriven {
    private TestDataDriven() {throw new IllegalStateException("Utility class");}

    public enum Element {
        TECHNOLOGY,
        CAPABILITY,
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

    public static CapabilityEntity getCapabilityEntity(Long id){
        CapabilityEntity entity = new CapabilityEntity();
        entity.setId(id);
        entity.setName(getValidName(id, Element.CAPABILITY));
        entity.setName(getValidDescription(id, Element.CAPABILITY));
        return entity;
    }
    public static List<CapabilityEntity> getListOfCapabilityEntity(int num){
        List<CapabilityEntity> entities = new ArrayList<>();
        for (long i = 1L ; i <= num ; i++) {
            entities.add(getCapabilityEntity(i));
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
