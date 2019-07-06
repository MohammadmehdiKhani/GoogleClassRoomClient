package com.example.classroomclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    ImageView imageView;
    Button choose_btn;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;


    EditText usernameEditText, passwordEditText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        imageView = (ImageView) findViewById(R.id.imageView);
        choose_btn = (Button) findViewById(R.id.choose_btn);
        choose_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openGallery();
            }
        });


        //Read from UI
        usernameEditText = findViewById(R.id.username_etx);
        passwordEditText = findViewById(R.id.password_etx);
        loginButton = findViewById(R.id.login_btn);


        TextWatcher usernameTextWatcher = new TextWatcher()
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
                int usernameEditTextLength = usernameEditText.getText().length();
                int passwordEditTextLength = passwordEditText.getText().length();

                if (usernameEditTextLength == 0)
                {
                    usernameEditText.setError("usernameEditText can not be empty");
                }

                if (usernameEditTextLength == 0 || passwordEditTextLength == 0
                        || (passwordEditTextLength > 0 && passwordEditTextLength < 5))
                {
                    loginButton.setEnabled(false);
                } else
                {
                    loginButton.setEnabled(true);
                }
            }
        };

        TextWatcher passwordTextWatcher = new TextWatcher()
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
                int usernameEditTextLength = usernameEditText.getText().length();
                int passwordEditTextLength = passwordEditText.getText().length();

                if (passwordEditTextLength == 0)
                {
                    passwordEditText.setError("passwordEditText can not be empty");
                }
                if (passwordEditTextLength < 5 && passwordEditTextLength > 0)
                {
                    passwordEditText.setError("passwordEditText can not be less than 5");
                }

                if (usernameEditTextLength == 0 || passwordEditTextLength == 0 || passwordEditTextLength < 5)
                {
                    loginButton.setEnabled(false);
                } else
                {
                    loginButton.setEnabled(true);
                }
            }
        };
        usernameEditText.addTextChangedListener(usernameTextWatcher);
        passwordEditText.addTextChangedListener(passwordTextWatcher);
    }

    private void openGallery()
    {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
        {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }

    public void onLoginClicked(View view)
    {
        try
        {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            //Create request
            ObjectMapper objectMapper = new ObjectMapper();
            RequestMeta meta = new RequestMeta("login");
            User userToRegister = new User(username, password);
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
                editor.putString("username", usernameEditText.getText().toString());
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


