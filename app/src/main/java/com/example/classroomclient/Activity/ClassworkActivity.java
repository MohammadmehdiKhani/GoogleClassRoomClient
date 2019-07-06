package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.classroomclient.Domain.Classroom;
import com.example.classroomclient.Domain.User;
import com.example.classroomclient.Helper.AssignmentsAdaptor;
import com.example.classroomclient.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class ClassworkActivity extends AppCompatActivity
{

    Button createAssignmentButton;
    ObjectMapper objectMapper;
    Classroom classroom;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classwork);

        objectMapper = new ObjectMapper();

        createAssignmentButton = findViewById(R.id.createAssignment_btn);

        try
        {
            String classroomString = getIntent().getStringExtra("classroom");
            classroom = objectMapper.readValue(classroomString, Classroom.class);

            SharedPreferences prefs = getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
            String loggedInUser = prefs.getString("username", null);

            boolean isTeacher = false;
            List<User> teachers = classroom.teachers;
            for (User teacher :
                    teachers)
            {
                if (teacher.username.equals(loggedInUser))
                {
                    isTeacher = true;
                    break;
                }
            }
            createAssignmentButton.setEnabled(isTeacher);

            final ListView assignmentListView = findViewById(R.id.assignment_lsv);
            AssignmentsAdaptor assignmentsAdaptor = new AssignmentsAdaptor(loggedInUser, classroom.assignments, classroom, getApplicationContext());
            assignmentListView.setAdapter(assignmentsAdaptor);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void onCreateAssignmentClicked(View view)
    {
        try
        {
            String classroomString = objectMapper.writeValueAsString(classroom);
            Intent createAssignmentIntent = new Intent(getBaseContext(), CreateAssignmentActivity.class);
            createAssignmentIntent.putExtra("classroom", classroomString);
            startActivity(createAssignmentIntent);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
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
        //Do nothing!
    }

    public void onPeopleClicked(View view)
    {
        try
        {
            String classroomString = objectMapper.writeValueAsString(classroom);
            Intent peopleIntent = new Intent(getBaseContext(), PeopleActivity.class);
            peopleIntent.putExtra("classroom", classroomString);
            startActivity(peopleIntent);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
