package com.pragma.bootcamp.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "bootcamp_version")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BootcampVersionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer maxParticipants;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "BOOTCAMP_ID")
    private BootcampEntity bootcamp;

}
