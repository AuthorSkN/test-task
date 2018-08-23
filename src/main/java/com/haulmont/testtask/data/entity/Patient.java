package com.haulmont.testtask.data.entity;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="patient")
public class Patient {

    @Id
    @Column(name="patient_id")
    private Long patientId;
    @Column(name="name")
    private String name;
    @Column(name="family_name")
    private String familyName;
    @Column(name="second_name")
    private String secondName;
    @Column(name="phone")
    private String phone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipeId", cascade = CascadeType.REMOVE)
    private Set<Recipe> recipes;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
