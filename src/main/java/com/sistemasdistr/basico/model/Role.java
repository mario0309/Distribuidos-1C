package com.sistemasdistr.basico.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String roleName;

    @Column(nullable = false)
    private Integer showOnCreate;

    public Role() {
    }

    public Role(Integer id, String roleName, Integer showOnCreate) {
        this.id = id;
        this.roleName = roleName;
        this.showOnCreate = showOnCreate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getShowOnCreate() {
        return showOnCreate;
    }

    public void setShowOnCreate(Integer showOnCreate) {
        this.showOnCreate = showOnCreate;
    }
}