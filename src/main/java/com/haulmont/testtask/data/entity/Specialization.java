package com.haulmont.testtask.data.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="specialization")
public class Specialization {

    @Id
    @Column(name="specialization_id")
    private Long specializationId;
    @Column(name="name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doctorId")
    private Set<Doctor> doctors;

    public Long getSpecializationId() {
        return specializationId;
    }

    public void setSpecializationId(Long specializationId) {
        this.specializationId = specializationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

}
