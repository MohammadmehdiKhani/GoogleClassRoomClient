package com.example.classroomclient.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.classroomclient.Domain.Classroom;
import com.example.classroomclient.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ClassroomActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        try
        {
            String classroomString = getIntent().getStringExtra("classroom");
            ObjectMapper objectMapper = new ObjectMapper();
            Classroom classroom = objectMapper.readValue(classroomString, Classroom.class);
            String temp = null;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
