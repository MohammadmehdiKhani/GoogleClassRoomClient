package com.example.classroomclient.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.classroomclient.Domain.Classroom;
import com.example.classroomclient.Domain.User;
import com.example.classroomclient.Helper.PeopleAdaptor;
import com.example.classroomclient.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class PeopleActivity extends AppCompatActivity
{

    Classroom classroom;
    ObjectMapper objectMapper;
    List<User> teachers;
    List<User> students;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        objectMapper = new ObjectMapper();

        //show list of students and teachers
        try
        {
            String classroomString = getIntent().getStringExtra("classroom");
            classroom = objectMapper.readValue(classroomString, Classroom.class);
            teachers = classroom.teachers;
            students = classroom.students;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        final ListView teachersListView = findViewById(R.id.teachers_lsv);
        PeopleAdaptor teachersAdaptor = new PeopleAdaptor(teachers, getApplicationContext());
        teachersListView.setAdapter(teachersAdaptor);

        final ListView studentsListView = findViewById(R.id.students_lsv);
        PeopleAdaptor studentsAdaptor = new PeopleAdaptor(students, getApplicationContext());
        studentsListView.setAdapter(studentsAdaptor);
    }

    public void onStreamClicked(View view)
    {
        try
        {
            String classroomString = objectMapper.writeValueAsString(classroom);
            Intent streamIntent = new Intent(getBaseContext(), StreamActivity.class);
            streamIntent.putExtra("classroom", classroomString);
            startActivity(streamIntent);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onClassworkClicked(View view)
    {
        try
        {
            String classroomString = objectMapper.writeValueAsString(classroom);
            Intent streamIntent = new Intent(getBaseContext(), ClassworkActivity.class);
            streamIntent.putExtra("classroom", classroomString);
            startActivity(streamIntent);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void onPeopleClicked(View view)
    {
    }
}
