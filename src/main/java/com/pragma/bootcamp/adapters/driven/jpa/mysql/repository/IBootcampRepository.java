package com.pragma.bootcamp.adapters.driven.jpa.mysql.repository;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampEntity;
import com.pragma.bootcamp.adapters.driven.jpa.mysql.util.AdapterConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IBootcampRepository extends JpaRepository<BootcampEntity, Long> {
    Optional<BootcampEntity> findByNameContaining(String name);
    Optional<BootcampEntity> findByName(String name);

    @Query(
            value = AdapterConstants.GET_ALL_BOOTCAMPS_CUSTOM_QUERY,
            nativeQuery = true
    )
    Page<BootcampEntity> findAll(Pageable pageable);
}
