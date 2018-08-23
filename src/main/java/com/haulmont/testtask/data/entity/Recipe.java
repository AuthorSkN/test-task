package com.haulmont.testtask.data.entity;


import javax.persistence.*;

@Entity
@Table(name="recipe")
public class Recipe {

    @Id
    @Column(name="recipe_id")
    private Long recipeId;
    @Column(name="desc")
    private String desc;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="patient_id", nullable = false)
    private Patient patient;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="doctor_id", nullable = false)
    private Doctor doctor;
    @Column(name="start_date")
    private String startDate;
    @Column(name="duration")
    private Integer duration;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="priority_id", nullable = false)
    private RecipePriority priority;

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public RecipePriority getPriority() {
        return priority;
    }

    public void setPriority(RecipePriority priority) {
        this.priority = priority;
    }
}
