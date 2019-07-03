package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.classroomclient.Domain.Request;
import com.example.classroomclient.Domain.RequestMeta;
import com.example.classroomclient.Domain.User;
import com.example.classroomclient.Helper.MessageSender;
import com.example.classroomclient.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onLoginClicked(View view)
    {
        try
        {
            //Read from UI
            EditText username = findViewById(R.id.username_etx);
            EditText password = findViewById(R.id.password_etx);

            if (username.length() == 0 || password.length() <= 5)
            {
                if (username.length() == 0)
                {
                    username.setError("Username can not be empty");
                }

                if (password.length() <= 5)
                {
                    password.setError("Password length can not be less than 5");
                }
                return;
            }

            //Create request
            ObjectMapper objectMapper = new ObjectMapper();
            RequestMeta meta = new RequestMeta("login");
            User userToRegister = new User(username.getText().toString(), password.getText().toString());
            Request request = new Request(meta, userToRegister);
            String requestString = objectMapper.writeValueAsString(request);

            //Send request and get response
            MessageSender messageSender = new MessageSender();
            JSONObject responseJson = messageSender.execute(requestString).get();

            //Parse response
            String metaString = responseJson.get("meta").toString();
            JSONObject metaJson = new JSONObject(metaString);
            String result = metaJson.get("result").toString();
            String message = metaJson.get("message").toString();

            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            if (result.equals("success"))
            {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Session", Context.MODE_PRIVATE); // 0 - for private mode
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username.getText().toString());
                editor.putString("password", username.getText().toString());
                editor.commit();

                Intent entranceIntent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(entranceIntent);
            }

        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }
}


