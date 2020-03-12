package com.example.sar.domain;

import com.example.sar.controllers.Users;
import lombok.Data;
import javax.persistence.*;
import java.util.Set;

@Entity
@Data

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    private Set<UserLogin> users;

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;

    }

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

    public Set<UserLogin> getUsers() {
        return users;
    }

    public void setUsers(Set<UserLogin> users) {
        this.users = users;
    }




}
