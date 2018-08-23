package com.haulmont.testtask.data.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="doctor")
public class Doctor {

    @Id
    @Column(name="doctor_id")
    private Long doctorId;
    @Column(name="name")
    private String name;
    @Column(name="family_name")
    private String familyName;
    @Column(name="second_name")
    private String secondName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="specialization_id", nullable=false)
    private Specialization specialization;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipeId", cascade = CascadeType.REMOVE)
    private Set<Recipe> recipes;


    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
