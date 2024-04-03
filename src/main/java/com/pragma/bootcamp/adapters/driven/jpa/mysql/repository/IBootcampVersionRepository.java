package com.pragma.bootcamp.adapters.driven.jpa.mysql.repository;

import com.pragma.bootcamp.adapters.driven.jpa.mysql.entity.BootcampVersionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBootcampVersionRepository extends JpaRepository<BootcampVersionEntity, Long> {

    Optional<BootcampVersionEntity> findByName(String name);
    Page<BootcampVersionEntity> findAll(Pageable pageable);
    Page<BootcampVersionEntity> findByBootcampId(Long bootcampId, Pageable pageable);

}
