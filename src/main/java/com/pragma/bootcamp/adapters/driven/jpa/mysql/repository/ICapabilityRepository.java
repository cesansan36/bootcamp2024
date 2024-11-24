package com.pragma.bootcamp.adapters.driven.jpa.mysql.repository;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapabilityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.AdapterConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ICapabilityRepository extends JpaRepository<CapabilityEntity, Long> {

    Optional<CapabilityEntity> findByName(String name);
    @Query(
            value = AdapterConstants.GET_ALL_CAPABILITIES_CUSTOM_QUERY,
            nativeQuery = true
    )
    Page<CapabilityEntity> findAll(Pageable pageable);
}
