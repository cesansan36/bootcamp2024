package com.pragma.bootcamp.configuration;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.BootcampAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.BootcampVersionAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.CapabilityAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.adapter.TechnologyAdapter;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IBootcampEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.IBootcampVersionEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ICapabilityEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.mapper.ITechnologyEntityMapper;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.IBootcampVersionRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ICapabilityRepository;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.repository.ITechnologyRepository;
import com.pragma.bootcamp.domain.primaryport.IBootcampServicePort;
import com.pragma.bootcamp.domain.primaryport.IBootcampVersionServicePort;
import com.pragma.bootcamp.domain.primaryport.ICapabilityServicePort;
import com.pragma.bootcamp.domain.primaryport.ITechnologyServicePort;
import com.pragma.bootcamp.domain.primaryport.usecase.BootcampUseCase;
import com.pragma.bootcamp.domain.primaryport.usecase.BootcampVersionUseCase;
import com.pragma.bootcamp.domain.primaryport.usecase.CapabilityUseCase;
import com.pragma.bootcamp.domain.primaryport.usecase.TechnologyUseCase;
import com.pragma.bootcamp.domain.secondaryport.IBootcampPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.IBootcampVersionPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.ICapabilityPersistencePort;
import com.pragma.bootcamp.domain.secondaryport.ITechnologyPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ITechnologyRepository technologyRepository;
    private final ITechnologyEntityMapper technologyEntityMapper;
    private final ICapabilityRepository capabilityRepository;
    private final ICapabilityEntityMapper capabilityEntityMapper;
    private final IBootcampRepository bootcampRepository;
    private final IBootcampEntityMapper bootcampEntityMapper;
    private final IBootcampVersionRepository bootcampVersionRepository;
    private final IBootcampVersionEntityMapper bootcampVersionEntityMapper;

    @Bean
    public ITechnologyPersistencePort technologyPersistencePort() {
        return new TechnologyAdapter(technologyRepository, technologyEntityMapper);
    }

    @Bean
    public ITechnologyServicePort technologyServicePort() {
        return new TechnologyUseCase(technologyPersistencePort());
    }


    @Bean
    public ICapabilityPersistencePort capabilityPersistencePort() {
        return new CapabilityAdapter(capabilityRepository, capabilityEntityMapper);
    }

    @Bean
    public ICapabilityServicePort capabilityServicePort() {
        return new CapabilityUseCase(capabilityPersistencePort());
    }

    @Bean
    public IBootcampPersistencePort bootcampPersistencePort() {
        return new BootcampAdapter(bootcampRepository, bootcampEntityMapper);
    }

    @Bean
    public IBootcampServicePort bootcampServicePort() {
        return new BootcampUseCase(bootcampPersistencePort());
    }

    @Bean
    public IBootcampVersionPersistencePort bootcampVersionPersistencePort() {
        return new BootcampVersionAdapter(bootcampVersionRepository, bootcampVersionEntityMapper);
    }

    @Bean
    public IBootcampVersionServicePort bootcampVersionServicePort() {
        return new BootcampVersionUseCase(bootcampVersionPersistencePort());
    }
}
