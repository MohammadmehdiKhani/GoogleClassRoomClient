package com.example.classroomclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.classroomclient.R;

public class EntranceActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
    }

    public void onRegisterClicked(View view)
    {
        Intent registerIntent = new Intent(getBaseContext(), RegisterActivity.class);
        startActivity(registerIntent);
    }

    public void onLoginClicked(View view)
    {
        Intent loginIntent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(loginIntent);
    }
}
