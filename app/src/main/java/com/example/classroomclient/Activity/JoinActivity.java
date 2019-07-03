package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        EditText code = findViewById(R.id.username_etx);
        String codeString = code.getText().toString();


        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String username = settings.getString("username", "");
        String password = settings.getString("password", "");

        MessageSender messageSender = new MessageSender();
        ObjectMapper objectMapper = new ObjectMapper();
        RequestMeta meta = new RequestMeta("joinClass");
        JoinClassCommand joinClassCommand=new JoinClassCommand(username,codeString,password);
        Request request = new Request(meta,joinClassCommand);
        String requestString = null;
        try
        {
            requestString = objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException e)
        {
            e.printStackTrace();
        }


        try
        {
            JSONObject responseJson = messageSender.execute(requestString).get();
            //Parse response
            String metaString = responseJson.get("meta").toString();
            JSONObject metaJson = new JSONObject(metaString);
            String result = metaJson.get("result").toString();
            String message = metaJson.get("message").toString();

            String joinedClassData = responseJson.get("data").toString();

            if (result.equals("success"))
            {
                Intent entranceIntent = new Intent(getBaseContext(), ClassroomActivity.class);
                entranceIntent.putExtra("classroom", joinedClassData);
                startActivity(entranceIntent);
            }


        } catch (ExecutionException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

    }


}
