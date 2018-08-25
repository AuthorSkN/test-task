package com.haulmont.testtask.data.entity;


import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name="recipe_priority")
public class RecipePriority implements Identifiable{

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    @Column(name="priority_id")
    private Long priorityId;
    @Column(name="name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipeId")
    private Set<Recipe> recipes;

    public RecipePriority(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return priorityId;
    }

    @Override
    public void setId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
