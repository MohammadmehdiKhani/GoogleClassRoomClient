package com.example.classroomclient.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.classroomclient.Command.CheckUsernameExistCommand;
import com.example.classroomclient.Domain.Request;
import com.example.classroomclient.Domain.RequestMeta;
import com.example.classroomclient.Domain.User;
import com.example.classroomclient.Helper.MessageSender;
import com.example.classroomclient.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity
{
    ImageView imageView;
    Button choose_btn;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    EditText usernameEditText, passwordEditText;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imageView = findViewById(R.id.imageView);
        choose_btn = findViewById(R.id.choose_btn);

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
        registerButton = findViewById(R.id.register_btn);

        //Validation
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
                boolean isUsernameReserved = false;

                if (usernameEditTextLength == 0)
                {
                    usernameEditText.setError("username can not be empty");
                }

                RequestMeta meta = new RequestMeta("isUsernameReserved");
                CheckUsernameExistCommand checkUsernameExistCommand =
                        new CheckUsernameExistCommand(usernameEditText.getText().toString());

                Request request = new Request(meta, checkUsernameExistCommand);
                ObjectMapper objectMapper = new ObjectMapper();
                try
                {
                    String requestString = objectMapper.writeValueAsString(request);

                    //Send request and get response
                    MessageSender messageSender = new MessageSender();
                    JSONObject responseJson = messageSender.execute(requestString).get();

                    //Parse response
                    String metaString = responseJson.get("meta").toString();
                    JSONObject metaJson = new JSONObject(metaString);
                    String result = metaJson.get("result").toString();
                    String message = metaJson.get("message").toString();

                    if (result.equals("success"))
                    {
                        usernameEditText.setError(message);
                        isUsernameReserved = true;
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                }


                if (usernameEditTextLength == 0 || passwordEditTextLength == 0
                        || (passwordEditTextLength > 0 && passwordEditTextLength < 5) || isUsernameReserved)
                {
                    registerButton.setEnabled(false);
                } else
                {
                    registerButton.setEnabled(true);
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
                    registerButton.setEnabled(false);
                } else
                {
                    registerButton.setEnabled(true);
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


    public void onRegisterClicked(View view)
    {
        try
        {
            //Read UI Value

            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            //Create Request
            User userToRegister = new User(username, password);
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



