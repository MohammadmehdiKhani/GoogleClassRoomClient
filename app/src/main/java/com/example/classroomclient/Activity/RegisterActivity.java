package com.example.classroomclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class RegisterActivity extends AppCompatActivity
{
	
	
//SHOROOE COMITE 2
    ImageView imageView;
    Button choose_btn;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    //PAYAN



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
		
		
//SHOROOE COMITE 2
        imageView = (ImageView) findViewById(R.id.imageView);
        choose_btn = (Button) findViewById(R.id.choose_btn);

        choose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        //PAYAN

    }

    public void onRegisterClicked(View view)
    {
        try
        {
            //Read UI Value
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

            //Create Request
            User userToRegister = new User(username.getText().toString(), password.getText().toString());
            RequestMeta meta = new RequestMeta("register");
            Request request = new Request(meta, userToRegister);
            ObjectMapper objectMapper = new ObjectMapper();
            String requestString = objectMapper.writeValueAsString(request);

            //Send request and get response
            MessageSender messageSender = new MessageSender();
            JSONObject responseJson = messageSender.execute(requestString).get();

            //Parse response
            String metaString = responseJson.get("meta").toString();
            JSONObject metaJson = new JSONObject(metaString);
            String result = metaJson.get("result").toString();
            String message = metaJson.get("message").toString();

            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();

            //Do action according to response
            if (result.equals("success"))
            {
                Intent entranceIntent = new Intent(getBaseContext(), EntranceActivity.class);
                startActivity(entranceIntent);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}


