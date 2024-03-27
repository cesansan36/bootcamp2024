package com.pragma.bootcamp.adapters.driven.jpa.mysql.repository;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.CapacityEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.AdapterConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ICapacityRepository extends JpaRepository<CapacityEntity, Long> {

    Optional<CapacityEntity> findByNameContaining(String name);
    Optional<CapacityEntity> findByName(String name);
    @Query(
            value = AdapterConstants.GET_ALL_CAPACITIES_CUSTOM_QUERY,
            nativeQuery = true
    )
    Page<CapacityEntity> findAll(Pageable pageable);
}
