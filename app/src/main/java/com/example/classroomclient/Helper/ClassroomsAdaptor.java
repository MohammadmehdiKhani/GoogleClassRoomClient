package com.example.classroomclient.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
        return convertView;
    }
}
