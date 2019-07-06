package com.example.classroomclient.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.classroomclient.Domain.User;
import com.example.classroomclient.R;
import java.util.List;

public class PeopleAdaptor extends BaseAdapter
{
    public List<User> users;
    private LayoutInflater layoutInflater;

    public PeopleAdaptor(List<User> persons, Context context)
    {
        this.users = persons;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount()
    {
        return users.size();
    }

    @Override
    public Object getItem(int position)
    {
        return users.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        convertView = layoutInflater.inflate(R.layout.layout_people, null);
        TextView usernameTextView = convertView.findViewById(R.id.username_txv);
        String username = users.get(position).username;
        usernameTextView.setText(username);
        return convertView;
    }
}
