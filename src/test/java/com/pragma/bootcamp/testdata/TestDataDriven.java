package com.pragma.bootcamp.testdata;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampVersionEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestDataDriven {
    private TestDataDriven() {throw new IllegalStateException("Utility class");}

    public enum Element {
        TECHNOLOGY,
        CAPABILITY,
        BOOTCAMP,
        BOOTCAMP_VERSION
    }

    public static final int VALID_MAX_PARTICIPANTS = 10;
    public static final String ENTITY_NAME = "%1$s name %2$s";
    public static final String ENTITY_DESCRIPTION = "%1$s description %2$s";

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
        entity.setDescription(getValidDescription(id, Element.TECHNOLOGY));
        return entity;
    }
    public static List<TechnologyEntity> getListOfTechnologyEntity(int technologyAmount){
        List<TechnologyEntity> entities = new ArrayList<>();
        for (long i = 1L ; i <= technologyAmount ; i++) {
            entities.add(getTechnologyEntity(i));
        }
        return entities;
    }

    public static CapabilityEntity getCapabilityEntity(Long id, int technologyAmount){
        CapabilityEntity entity = new CapabilityEntity();
        entity.setId(id);
        entity.setName(getValidName(id, Element.CAPABILITY));
        entity.setDescription(getValidDescription(id, Element.CAPABILITY));
        entity.setTechnologies(getListOfTechnologyEntity(technologyAmount));
        return entity;
    }
    public static List<CapabilityEntity> getListOfCapabilityEntity(int capabilityAmount, int technologyAmount){
        List<CapabilityEntity> entities = new ArrayList<>();
        for (long i = 1L ; i <= capabilityAmount ; i++) {
            entities.add(getCapabilityEntity(i, technologyAmount));
        }
        return entities;
    }

    public static BootcampEntity getBootcampEntity(Long id, int capabilityAmount, int technologyAmount){
        BootcampEntity entity = new BootcampEntity();
        entity.setId(id);
        entity.setName(getValidName(id, Element.BOOTCAMP));
        entity.setDescription(getValidDescription(id, Element.BOOTCAMP));
        entity.setCapabilities(getListOfCapabilityEntity(capabilityAmount, technologyAmount));
        return entity;
    }
    public static List<BootcampEntity> getListOfBootcampEntity(int bootcampAmount, int capabilityAmount, int technologyAmount){
        List<BootcampEntity> entities = new ArrayList<>();
        for (long i = 1L ; i <= bootcampAmount ; i++) {
            entities.add(getBootcampEntity(i, capabilityAmount, technologyAmount));
        }
        return entities;
    }

    public static BootcampVersionEntity getBootcampVersionEntity(Long id, int separationBetweenDates, int capabilityAmount, int technologyAmount){
        BootcampVersionEntity entity = new BootcampVersionEntity();
        entity.setId(id);
        entity.setName(getValidName(id, Element.BOOTCAMP_VERSION));
        entity.setMaxParticipants(VALID_MAX_PARTICIPANTS);
        entity.setStartDate(LocalDate.now());
        entity.setEndDate(LocalDate.now().plusDays(separationBetweenDates));
        entity.setBootcamp(getBootcampEntity(id, capabilityAmount, technologyAmount));

        return entity;
    }

    public static List<BootcampVersionEntity> getListOfBootcampVersionEntity(int bootcampVersionAmount, int separationBetweenDates, int capabilityAmount, int technologyAmount){
        List<BootcampVersionEntity> entities = new ArrayList<>();
        for (long i = 1L ; i <= bootcampVersionAmount ; i++) {
            entities.add(getBootcampVersionEntity(i, separationBetweenDates, capabilityAmount, technologyAmount));
        }
        return entities;

    }
}
