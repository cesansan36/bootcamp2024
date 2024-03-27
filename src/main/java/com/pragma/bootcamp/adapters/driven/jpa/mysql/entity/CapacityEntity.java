package com.pragma.bootcamp.adapters.driven.jpa.mysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "capacity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CapacityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "capacity_technology",
            joinColumns = @JoinColumn(name = "ID_CAPACITY"),
            inverseJoinColumns = @JoinColumn(name = "ID_TECHNOLOGY")
    )
    @JsonIgnore
    private List<TechnologyEntity> technologies;
}
