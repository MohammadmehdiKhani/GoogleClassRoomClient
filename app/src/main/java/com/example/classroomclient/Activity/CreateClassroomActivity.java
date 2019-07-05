package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.classroomclient.Command.CreateClassCommand;
import com.example.classroomclient.Domain.Classroom;
import com.example.classroomclient.Domain.Request;
import com.example.classroomclient.Domain.RequestMeta;
import com.example.classroomclient.Helper.MessageSender;
import com.example.classroomclient.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

public class CreateClassroomActivity extends AppCompatActivity
{

    EditText name,description,room;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createclassroom);

        name = findViewById(R.id.name_etx);
        room = findViewById(R.id.room_etx);
        description= findViewById(R.id.description_etx);

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {//BAAD AZ INKE KARBAR POR KARD FIELD HA RO BEHESH PAYAM NESHON MIDIM NA BAAD AZ ZADAN E DOKME

            @Override
            public void onFocusChange(View view, boolean b) {

                if (name.getText().length() == 0) {
                    name.setError("name can not be empty");
                }

            }

        });
        room.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {

                if (room.getText().length() == 0) {
                    room.setError("room can not be empty");
                }

            }

        });


    }



    public void onCreateClicked(View view)
    {
        try
        {
            String name1 = name.getText().toString();
            String room1 = room.getText().toString();

            if (name1.length() != 0 && room1.length() != 0)
            {
                Toast.makeText(this, "you can createyour class!!", Toast.LENGTH_LONG).show();


            }
            else {
                Toast.makeText(this, "please fix the errors first", Toast.LENGTH_LONG).show();
            }

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

                Intent entranceIntent = new Intent(getBaseContext(), ClassroomActivity.class);
                entranceIntent.putExtra("classroom", dataString);
                startActivity(entranceIntent);
            }
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}