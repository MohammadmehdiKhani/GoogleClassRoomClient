package com.example.classroomclient.Domain;

public class Assignment
{
    public String title;
    public String description;
    public int points;

    public Assignment()
    {
    }

    public Assignment(String title, String description, int points)
    {
        this.title = title;
        this.description = description;
        this.points = points;
    }
}
