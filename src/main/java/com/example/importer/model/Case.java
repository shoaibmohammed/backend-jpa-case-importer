package com.example.importer.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table (name="cases", uniqueConstraints = { @UniqueConstraint(columnNames= {"org_id", "mrn"} ) } )
public class Case {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    @SequenceGenerator(name = "id_seq", sequenceName ="id_seq")
    Long id;

    @ManyToOne
    @JoinColumn (name = "org_id", nullable = false)
    @Getter
    @Setter
    Organization org;

    @Getter
    @Setter
    String mrn;

    @Getter
    @Setter
    String name;

}
