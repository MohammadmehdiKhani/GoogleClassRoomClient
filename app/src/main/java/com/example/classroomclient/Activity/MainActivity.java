package com.example.classroomclient.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.classroomclient.R;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
