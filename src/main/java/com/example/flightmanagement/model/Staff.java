package com.example.flightmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Table(name = "staff")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Staff {

    @Id
    @Column(length = 36)
    private String id;

    @NotBlank(message = "Name darf nicht leer sein")
    private String name;

    public Staff() {
        this.id = "";
        this.name = "";
    }

    public Staff(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @PrePersist
    public void ensureId() {
        if (this.id == null || this.id.isBlank()) {
            this.id = UUID.randomUUID().toString();
        }
    }

    // getters / setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Staff{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}
