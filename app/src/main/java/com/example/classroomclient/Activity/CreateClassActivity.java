package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.classroomclient.Command.CreateClassCommand;
import com.example.classroomclient.Domain.Request;
import com.example.classroomclient.Domain.RequestMeta;
import com.example.classroomclient.Helper.MessageSender;
import com.example.classroomclient.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class CreateClassActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createclass);
    }

    public void onCreateClicked(View view) throws JsonProcessingException, ExecutionException, InterruptedException
    {
        EditText nameEditText = findViewById(R.id.name_etx);
        EditText descriptionEditText = findViewById(R.id.description_etx);
        EditText roomEditText = findViewById(R.id.room_etx);

        String name = nameEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String room = roomEditText.getText().toString();

        if (nameEditText.length() == 0 || roomEditText.length() == 0)
        {
            if (roomEditText.length() == 0)
            {
                roomEditText.setError("Room can not be empty");
            }

            if (nameEditText.length() == 0)
            {
                nameEditText.setError("Name length can not be empty");
            }
            return;
        }

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);

        CreateClassCommand createClassCommand = new CreateClassCommand(username, name, description, room);
        RequestMeta meta = new RequestMeta("createClass");
        Request request = new Request(meta, createClassCommand);

        ObjectMapper objectMapper = new ObjectMapper();
        String requestString = objectMapper.writeValueAsString(request);

        MessageSender messageSender = new MessageSender();
        JSONObject responseJson = messageSender.execute(requestString).get();
    }
}
