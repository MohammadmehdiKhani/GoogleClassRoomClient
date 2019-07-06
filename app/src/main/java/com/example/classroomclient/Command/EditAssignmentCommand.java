package com.example.classroomclient.Command;

public class EditAssignmentCommand
{
    public String title;
    public String description;
    public int points;

    public EditAssignmentCommand()
    {
    }

    public EditAssignmentCommand(String title, String description, int points)
    {
        this.title = title;
        this.description = description;
        this.points = points;
    }

}
