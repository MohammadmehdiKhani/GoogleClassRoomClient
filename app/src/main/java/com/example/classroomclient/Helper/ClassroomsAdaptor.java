package com.example.classroomclient.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classroomclient.Domain.Classroom;
import com.example.classroomclient.Domain.User;
import com.example.classroomclient.R;

import java.util.List;


public class ClassroomsAdaptor extends BaseAdapter
{
    public String loggedInUser;
    public List<Classroom> classrooms;
    private LayoutInflater layoutInflater;

    public ClassroomsAdaptor(String loggedInUser, List<Classroom> classrooms, Context context)
    {
        this.loggedInUser = loggedInUser;
        this.classrooms = classrooms;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return classrooms.size();
    }

    @Override
    public Object getItem(int position)
    {
        return classrooms.get(position);
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
            convertView = layoutInflater.inflate(R.layout.layout_classroom, null);
            TextView className = convertView.findViewById(R.id.className_txv);
            TextView teacherNameOrStudentNumber = convertView.findViewById(R.id.teacherNameOrStudentNumber_txv);
            final Button unEnrollOrChangeClassInfo = convertView.findViewById(R.id.unEnrollOrChangeClassInfo_btn);

            String name = classrooms.get(position).name;
            className.setText(name);

            boolean isTeacher = false;
            List<User> teachers = classrooms.get(position).teachers;
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
                teacherNameOrStudentNumber.setText(Integer.toString(classrooms.get(position).students.size()));
            } else
            {
                teacherNameOrStudentNumber.setText(teachers.get(0).username);
            }

            final boolean finalIsTeacher = isTeacher;
            if (finalIsTeacher == true)
            {
                unEnrollOrChangeClassInfo.setText("Edit class");
            } else
            {
                unEnrollOrChangeClassInfo.setText("UnEnroll");
            }
            unEnrollOrChangeClassInfo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (finalIsTeacher == true)
                    {
                        Toast.makeText(layoutInflater.getContext(), "Go to class", Toast.LENGTH_LONG).show();
                    } else
                    {
                        Toast.makeText(layoutInflater.getContext(), "Delete Enrollment", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return convertView;
    }
}


