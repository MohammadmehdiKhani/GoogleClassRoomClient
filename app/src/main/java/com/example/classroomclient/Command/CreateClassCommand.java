package com.example.classroomclient.Command;

public class CreateClassCommand
{
    public String teacher;
    public String name;
    public String description;
    public String room;

    public CreateClassCommand()
    {
    }

    public CreateClassCommand(String teacher, String name, String description, String room)
    {
        this.teacher = teacher;
        this.room = room;
        this.description = description;
        this.name = name;
    }
}



