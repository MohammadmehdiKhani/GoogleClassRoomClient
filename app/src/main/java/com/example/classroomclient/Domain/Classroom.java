package com.example.classroomclient.Domain;

import java.util.ArrayList;
import java.util.List;

public class Classroom
{
    public String name;
    public String description;
    public String room;
    public String code;

    public List<User> teachers;
    public List<User> students;
    public List<Assignment> assignments;

    public Classroom()
    {
        teachers = new ArrayList<>();
        students = new ArrayList<>();
        assignments = new ArrayList<>();
    }

    public Classroom(String name, String description, String room, String code)
    {
        this();
        this.name = name;
        this.description = description;
        this.room = room;
        this.code = code;
    }

    public void addTeacher(User teacher)
    {
        teachers.add(teacher);
    }

    public void addStudent(User student)
    {
        students.add(student);
    }

    public void addAssignment(Assignment assignment)
    {
        assignments.add(assignment);
    }

    public void emptyLists()
    {
        teachers = new ArrayList<>();
        students = new ArrayList<>();
        assignments = new ArrayList<>();
    }
}
