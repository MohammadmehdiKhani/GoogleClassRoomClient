package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.classroomclient.Command.CreateClassCommand;
import com.example.classroomclient.Command.GetClassroomsOfUser;
import com.example.classroomclient.Domain.Classroom;
import com.example.classroomclient.Domain.Request;
import com.example.classroomclient.Domain.RequestMeta;
import com.example.classroomclient.Helper.ClassroomsAdaptor;
import com.example.classroomclient.Helper.MessageSender;
import com.example.classroomclient.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);

            //Create request
            GetClassroomsOfUser getClassroomsOfUser = new GetClassroomsOfUser(username);
            RequestMeta meta = new RequestMeta("getClassroomsOfUser");
            Request request = new Request(meta, getClassroomsOfUser);
            ObjectMapper objectMapper = new ObjectMapper();
            String requestString = objectMapper.writeValueAsString(request);
            MessageSender messageSender = new MessageSender();
            JSONObject responseJson = messageSender.execute(requestString).get();

            //Parse response
            String metaString = responseJson.get("meta").toString();
            JSONObject metaJson = new JSONObject(metaString);
            String result = metaJson.get("result").toString();
            String message = metaJson.get("message").toString();

            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

            if (result.equals("success"))
            {
                String dataString = responseJson.get("data").toString();
                List<Classroom> classrooms = objectMapper.readValue(dataString, new TypeReference<List<Classroom>>()
                {
                });
                final ListView classroomsListView = findViewById(R.id.classrooms_lsv);
                ClassroomsAdaptor classroomsAdaptor = new ClassroomsAdaptor(username, classrooms, getApplicationContext());
                classroomsListView.setAdapter(classroomsAdaptor);

                classroomsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
                    {
                        Classroom classroom = (Classroom) classroomsListView.getItemAtPosition(position);
                        Toast.makeText(MainActivity.this, "Selected :" + " " + classroom.name + ", " + classroom.room, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void onJoinClicked(View view)
    {
        Intent joinClassIntent = new Intent(getBaseContext(), JoinActivity.class);
        startActivity(joinClassIntent);
    }

    public void onCreateClicked(View view)
    {
        Intent createClassIntent = new Intent(getBaseContext(), CreateClassroomActivity.class);
        startActivity(createClassIntent);
    }
}
