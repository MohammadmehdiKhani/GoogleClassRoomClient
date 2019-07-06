package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.classroomclient.Command.CreateClassCommand;
import com.example.classroomclient.Domain.Request;
import com.example.classroomclient.Domain.RequestMeta;
import com.example.classroomclient.Helper.MessageSender;
import com.example.classroomclient.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

public class CreateClassroomActivity extends AppCompatActivity
{
    EditText nameEditText, descriptionEditText, roomEditText;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createclassroom);

        nameEditText = findViewById(R.id.name_etx);
        roomEditText = findViewById(R.id.room_etx);
        descriptionEditText = findViewById(R.id.description_etx);
        createButton = findViewById(R.id.create_btn);

        TextWatcher nameTextWatcher = new TextWatcher()
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
                int nameEditTextLength = nameEditText.getText().length();
                int roomEditTextLength = roomEditText.getText().length();

                if (nameEditTextLength == 0)
                {
                    nameEditText.setError("name can not be empty");
                }

                if (nameEditTextLength == 0 || roomEditTextLength == 0)
                {
                    createButton.setEnabled(false);
                } else
                {
                    createButton.setEnabled(true);
                }
            }
        };

        TextWatcher roomTextWatcher = new TextWatcher()
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
                int nameEditTextLength = nameEditText.getText().length();
                int roomEditTextLength = roomEditText.getText().length();

                if (roomEditTextLength == 0)
                {
                    roomEditText.setError("room can not be empty");
                }

                if (nameEditTextLength == 0 || roomEditTextLength == 0)
                {
                    createButton.setEnabled(false);
                } else
                {
                    createButton.setEnabled(true);
                }
            }
        };

        nameEditText.addTextChangedListener(nameTextWatcher);
        roomEditText.addTextChangedListener(roomTextWatcher);
    }

    public void onCreateClicked(View view)
    {
        try
        {
            String name = nameEditText.getText().toString();
            String room = roomEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);

            //Create request
            CreateClassCommand createClassCommand = new CreateClassCommand(username, name, description, room);
            RequestMeta meta = new RequestMeta("createClass");
            Request request = new Request(meta, createClassCommand);

            ObjectMapper objectMapper = new ObjectMapper();
            String requestString = objectMapper.writeValueAsString(request);

            MessageSender messageSender = new MessageSender();
            JSONObject responseJson = messageSender.execute(requestString).get();

            //Parse response
            String metaString = responseJson.get("meta").toString();
            JSONObject metaJson = new JSONObject(metaString);
            String result = metaJson.get("result").toString();
            String message = metaJson.get("message").toString();

            Toast.makeText(CreateClassroomActivity.this, message, Toast.LENGTH_LONG).show();

            if (result.equals("success"))
            {
                String dataString = responseJson.get("data").toString();

                Intent streamIntent = new Intent(getBaseContext(), StreamActivity.class);
                streamIntent.putExtra("classroom", dataString);
                startActivity(streamIntent);
            }
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}