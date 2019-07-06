package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.classroomclient.Domain.Classroom;
import com.example.classroomclient.Domain.User;
import com.example.classroomclient.Helper.AssignmentsAdaptor;
import com.example.classroomclient.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class StreamActivity extends AppCompatActivity
{

    Classroom classroom;
    ObjectMapper objectMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        objectMapper = new ObjectMapper();
        //Show list of asses
        try
        {
            String classroomString = getIntent().getStringExtra("classroom");
            classroom = objectMapper.readValue(classroomString, Classroom.class);

            final ListView assignmentListView = findViewById(R.id.assignment_lsv);
            AssignmentsAdaptor assignmentsAdaptor = new AssignmentsAdaptor(null,classroom.assignments, classroom, getApplicationContext());
            assignmentListView.setAdapter(assignmentsAdaptor);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            String classroomString = getIntent().getStringExtra("classroom");
            objectMapper = new ObjectMapper();
            classroom = objectMapper.readValue(classroomString, Classroom.class);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onStreamClicked(View view)
    {

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
        try
        {
            String classroomString = objectMapper.writeValueAsString(classroom);
            Intent streamIntent = new Intent(getBaseContext(), PeopleActivity.class);
            streamIntent.putExtra("classroom", classroomString);
            startActivity(streamIntent);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
