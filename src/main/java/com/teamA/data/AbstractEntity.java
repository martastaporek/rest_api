package com.teamA.data;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public AbstractEntity(long id) {
        this.id = id;
    }

    public AbstractEntity() { }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}
