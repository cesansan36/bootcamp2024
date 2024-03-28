package com.pragma.bootcamp.testdata;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.TechnologyEntity;
import com.pragma.bootcamp.domain.model.Technology;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    private TestData () {throw new IllegalStateException("Utility class");}

    public static final Long TECHNOLOGY_ID_1 = 1L;
    public static final String TECHNOLOGY_NAME_1 = "Java";
    public static final String TECHNOLOGY_DESCRIPTION_1 = "Not Python";
    public static final Long TECHNOLOGY_ID_2 = 2L;
    public static final String TECHNOLOGY_NAME_2 = "Python";
    public static final String TECHNOLOGY_DESCRIPTION_2 = "Not Java";

    public static Technology getTestTechnology1 ()
    {
        return new Technology(TECHNOLOGY_ID_1, TECHNOLOGY_NAME_1, TECHNOLOGY_DESCRIPTION_1);
    }
    public static Technology getTestTechnology2 ()
    {
        return new Technology(TECHNOLOGY_ID_2, TECHNOLOGY_NAME_2, TECHNOLOGY_DESCRIPTION_2);
    }
    public static TechnologyEntity getTestTechnologyEntity1 ()
    {
        return new TechnologyEntity(TECHNOLOGY_ID_1, TECHNOLOGY_NAME_1, TECHNOLOGY_DESCRIPTION_1, new ArrayList <CapacityEntity>());
    }
    public static TechnologyEntity getTestTechnologyEntity2 ()
    {
        return new TechnologyEntity(TECHNOLOGY_ID_2, TECHNOLOGY_NAME_2, TECHNOLOGY_DESCRIPTION_2, new ArrayList<CapacityEntity>());
    }
}
