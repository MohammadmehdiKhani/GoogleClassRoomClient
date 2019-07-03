package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.classroomclient.Command.JoinClassCommand;
import com.example.classroomclient.Domain.Request;
import com.example.classroomclient.Domain.RequestMeta;
import com.example.classroomclient.Domain.User;
import com.example.classroomclient.Helper.MessageSender;
import com.example.classroomclient.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    public void onJoinClicked(View view)
    {
        try
        {
            //Read from UI
            EditText code = findViewById(R.id.classCode_etx);
            String codeString = code.getText().toString();

            SharedPreferences prefs = getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE);
            String username = prefs.getString("username", null);

            //Create request
            MessageSender messageSender = new MessageSender();
            ObjectMapper objectMapper = new ObjectMapper();
            RequestMeta meta = new RequestMeta("joinClass");
            JoinClassCommand joinClassCommand = new JoinClassCommand(username, codeString);
            Request request = new Request(meta, joinClassCommand);

            String requestString = objectMapper.writeValueAsString(request);

            JSONObject responseJson = messageSender.execute(requestString).get();

            //Parse response
            String metaString = responseJson.get("meta").toString();
            JSONObject metaJson = new JSONObject(metaString);
            String result = metaJson.get("result").toString();
            String message = metaJson.get("message").toString();
            String joinedClassData = responseJson.get("data").toString();

            Toast.makeText(JoinActivity.this, message, Toast.LENGTH_LONG).show();
            if (result.equals("success"))
            {
                Intent entranceIntent = new Intent(getBaseContext(), ClassroomActivity.class);
                entranceIntent.putExtra("classroom", joinedClassData);
                startActivity(entranceIntent);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
