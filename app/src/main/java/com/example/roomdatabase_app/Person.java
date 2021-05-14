package com.example.roomdatabase_app;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Person {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "name")
    private String person;

    @Ignore
    public Person(int id, String person) {
        this.id = id;
        this.person = person;
    }

    public Person(String person) {
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return  "{" + id + "}" + person ;
    }
}
