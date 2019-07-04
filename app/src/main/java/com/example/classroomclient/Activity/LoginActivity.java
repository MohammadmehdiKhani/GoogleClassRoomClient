package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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


    //SHOROOE COMITE 2
    ImageView imageView;
    Button choose_btn;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    //PAYAN


    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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


        //Read from UI
        username = findViewById(R.id.username_etx);
        password = findViewById(R.id.password_etx);

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {//BAAD AZ INKE KARBAR POR KARD FIELD HA RO BEHESH PAYAM NESHON MIDIM NA BAAD AZ ZADAN E DOKME

            @Override
            public void onFocusChange(View view, boolean b) {

                if (username.getText().length() == 0) {
                    username.setError("username can not be empty");
                }

            }

        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {

                if (password.getText().length() == 0) {
                    password.setError("password can not be empty");
                }
                else if(password.getText().length()<=

                        5){
                    password.setError("password should have more than 5 characters ");
                }

            }

        });


    }

    //SHOROOE COMITE 2
    private void openGallery() {

        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }

    }

    //PAYAN

    public void onLoginClicked(View view)
    {
        try
        {


            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            if (user.length() != 0 && pass.length() > 5) {// AGE MOSHKELI NABOOD VARED MISHE
                Toast.makeText(this, "you can enter!", Toast.LENGTH_LONG).show();
            } else {//AGE MOSHKELI BOOD BEHESH MIGIM HALLESH KON BAAD VARED SHO
                Toast.makeText(this, "please fix the errors first", Toast.LENGTH_LONG).show();
            }

            //Create request
            ObjectMapper objectMapper = new ObjectMapper();
            RequestMeta meta = new RequestMeta("login");
            User userToRegister = new User(username.getText().toString(), password.getText().toString());
            Request request = new Request(meta,userToRegister);
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


