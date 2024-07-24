package com.fils.fils.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "office")
    private String office;

    @Column(name = "password")
    private String password;

    @Column(name = "project", nullable = false)
    private String project;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "remaining_days")
    private Integer remainingDays;

    @Column(name = "school")
    private String school;

    @Column(name = "username")
    private String username;

    // Constructors
    public User() {
    }

    public User(String name, String project, String projectDescription, int remainingDays, String office, String school, String username, String password) {
        this.name = name;
        this.project = project;
        this.projectDescription = projectDescription;
        this.remainingDays = remainingDays;
        this.office = office;
        this.school = school;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public int getRemainingDays() {
        return remainingDays;
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

