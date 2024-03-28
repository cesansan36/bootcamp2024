package com.pragma.bootcamp.adapters.driven.jpa.mysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "bootcamp")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BootcampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "capacity_bootcamp",
            joinColumns = @JoinColumn(name = "ID_BOOTCAMP"),
            inverseJoinColumns = @JoinColumn(name = "ID_CAPACITY")
    )
    @JsonIgnore
    private List<CapacityEntity> capacities;
}
