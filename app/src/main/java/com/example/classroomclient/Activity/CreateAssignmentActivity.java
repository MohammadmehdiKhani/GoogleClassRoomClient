package com.example.classroomclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.classroomclient.Command.CreateAssignmentCommand;
import com.example.classroomclient.Domain.Assignment;
import com.example.classroomclient.Domain.Classroom;
import com.example.classroomclient.Domain.Request;
import com.example.classroomclient.Domain.RequestMeta;
import com.example.classroomclient.Helper.MessageSender;
import com.example.classroomclient.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;

public class CreateAssignmentActivity extends AppCompatActivity
{

    EditText titleEditText, descriptionEditText, pointsEditText;
    Button createButton;
    ObjectMapper objectMapper;
    Classroom classroom;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createassignment);

        try
        {
            String classroomString = getIntent().getStringExtra("classroom");
            objectMapper = new ObjectMapper();
            classroom = objectMapper.readValue(classroomString, Classroom.class);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        titleEditText = findViewById(R.id.title_etx);
        descriptionEditText = findViewById(R.id.description_etx);
        pointsEditText = findViewById(R.id.points_etx);
        createButton = findViewById(R.id.createAssignment_btn);

        TextWatcher titleTextWatcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                int titleEditTextLength = titleEditText.getText().length();


                if (titleEditTextLength == 0)
                {
                    titleEditText.setError("title can not be empty");
                    createButton.setEnabled(false);
                } else
                {
                    createButton.setEnabled(true);
                }
            }
        };

        titleEditText.addTextChangedListener(titleTextWatcher);
    }

    public void onCreateAssignmentClicked(View view)
    {
        try
        {
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();
            int points = Integer.valueOf(pointsEditText.getText().toString());

            //Create request
            CreateAssignmentCommand createAssignmentCommand = new CreateAssignmentCommand(title, description, points, classroom.code);
            RequestMeta meta = new RequestMeta("createAssignment");
            Request request = new Request(meta, createAssignmentCommand);

            String requestString = objectMapper.writeValueAsString(request);

            MessageSender messageSender = new MessageSender();
            JSONObject responseJson = messageSender.execute(requestString).get();

            //Parse response
            String metaString = responseJson.get("meta").toString();
            JSONObject metaJson = new JSONObject(metaString);
            String result = metaJson.get("result").toString();
            String message = metaJson.get("message").toString();

            Toast.makeText(CreateAssignmentActivity.this, message, Toast.LENGTH_LONG).show();

            if (result.equals("success"))
            {
                String dataString = responseJson.get("data").toString();
                Assignment assignment = objectMapper.readValue(dataString, Assignment.class);
                classroom.addAssignment(assignment);

                Intent classworkIntent = new Intent(getBaseContext(), ClassworkActivity.class);
                classworkIntent.putExtra("classroom", objectMapper.writeValueAsString(classroom));
                startActivity(classworkIntent);
            }
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
