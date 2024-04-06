package com.pragma.bootcamp.adapters.driven.jpa.mysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "capability")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CapabilityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @ManyToMany
    @JoinTable(
            name = "capability_technology",
            joinColumns = @JoinColumn(name = "ID_CAPABILITY"),
            inverseJoinColumns = @JoinColumn(name = "ID_TECHNOLOGY")
    )
    @JsonIgnore
    private List<TechnologyEntity> technologies;

    @ManyToMany(mappedBy = "capabilities")
    private List<BootcampEntity> bootcamps;
}
