package com.example.classroomclient.Domain;

import java.util.ArrayList;
import java.util.List;

public class User
{
    public String username;
    public String password;
    public List<Classroom> creates;
    public List<Classroom> joins;

    public User() {
        creates = new ArrayList<>();
        joins = new ArrayList<>();
    }

    public User(String username, String password) {
        this();
        this.username = username;
        this.password = password;
    }

    public void addClassroomToCreates(Classroom classroom) {
        creates.add(classroom);
    }

    public void emptyLists(){
        creates = new ArrayList<>();
        joins = new ArrayList<>();
    }
}
