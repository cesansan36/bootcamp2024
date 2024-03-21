package com.pragma.bootcamp.adapters.driven.jpa.mysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "technology")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TechnologyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany(mappedBy = "technologies")
//    @JsonIgnore
    private List<CapacityEntity> capacities;
}
