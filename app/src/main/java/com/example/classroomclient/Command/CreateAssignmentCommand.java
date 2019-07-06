package com.example.classroomclient.Command;

public class CreateAssignmentCommand
{
    public String title;
    public String description;
    public int points;
    public String code;

    public CreateAssignmentCommand()
    {
    }

    public CreateAssignmentCommand(String title, String description, int points, String code)
    {
        this.title = title;
        this.description = description;
        this.points = points;
        this.code = code;
    }
}
