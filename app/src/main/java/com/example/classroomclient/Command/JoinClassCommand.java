package com.example.classroomclient.Command;

public class JoinClassCommand
{
    public String username;
    public String code;

    public JoinClassCommand()
    {
    }

    public JoinClassCommand(String username, String code)
    {
        this.username = username;
        this.code = code;
    }
}
