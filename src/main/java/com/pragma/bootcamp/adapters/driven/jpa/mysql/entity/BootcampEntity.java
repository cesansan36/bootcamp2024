package com.pragma.bootcamp.adapters.driven.jpa.mysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
            name = "capability_bootcamp",
            joinColumns = @JoinColumn(name = "ID_BOOTCAMP"),
            inverseJoinColumns = @JoinColumn(name = "ID_CAPABILITY")
    )
    @JsonIgnore
    private List<CapabilityEntity> capabilities;
}
