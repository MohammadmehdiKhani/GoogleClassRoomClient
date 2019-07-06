package com.example.classroomclient.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classroomclient.Domain.Assignment;
import com.example.classroomclient.Domain.Classroom;
import com.example.classroomclient.Domain.User;
import com.example.classroomclient.R;

import java.util.List;

public class AssignmentsAdaptor extends BaseAdapter
{
    public String loggedInUser;
    public List<Assignment> assignments;
    public Classroom classroom;
    private LayoutInflater layoutInflater;

    public AssignmentsAdaptor(String loggedInUser, List<Assignment> assignments, Classroom classroom, Context context)
    {
        this.loggedInUser = loggedInUser;
        this.assignments = assignments;
        this.classroom = classroom;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return assignments.size();
    }

    @Override
    public Object getItem(int position)
    {
        return assignments.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        try
        {
            convertView = layoutInflater.inflate(R.layout.layout_assignment, null);
            TextView titleTextView = convertView.findViewById(R.id.title_txv);
            final Button editButton = convertView.findViewById(R.id.edit_btn);

            //For Stream activity
            if (loggedInUser==null)
            {
                editButton.setVisibility(View.INVISIBLE);
            }

            String title = assignments.get(position).title;
            titleTextView.setText(title);

            boolean isTeacher = false;
            List<User> teachers = classroom.teachers;
            for (User teacher :
                    teachers)
            {
                if (teacher.username.equals(loggedInUser))
                {
                    isTeacher = true;
                    break;
                }
            }

            if (isTeacher)
            {
                editButton.setEnabled(true);
            } else
            {
                editButton.setEnabled(false);
            }

            editButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(layoutInflater.getContext(), "Go to edit assignment", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return convertView;
    }
}
