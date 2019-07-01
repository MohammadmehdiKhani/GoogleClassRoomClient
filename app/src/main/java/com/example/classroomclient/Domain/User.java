package com.example.classroomclient.Domain;

import java.util.ArrayList;
import java.util.List;

public class User
{
    public String username;
    public String password;
    public List<Class> creates;
    public List<Class> joins;

    public User(String username, String password){
        this.username = username;
        this.password = password;
        
        creates = new ArrayList<Class>();
        joins = new ArrayList<Class>();
    }
}
