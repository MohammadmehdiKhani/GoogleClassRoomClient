package com.example.classroomclient.Helper;

import android.app.Activity;
import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class MessageSender extends AsyncTask<String, Void, JSONObject>
{
    private int _port = 6666;
    private String _host = "192.168.43.242";

    public MessageSender(){
    }

    @Override
    protected JSONObject doInBackground(String... strings)
    {
        JSONObject responseJson = new JSONObject();

        try{
            Socket socket = new Socket(_host, _port);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            dataOutputStream.writeUTF(strings[0]);
            dataOutputStream.flush();

            String response = dataInputStream.readUTF();
            responseJson = new JSONObject(response);

            dataOutputStream.close();
            socket.close();

        }catch(Exception exception){
            Exception ex = exception;
        }

        return responseJson;
    }

//    @Override
//    protected void onPostExecute(JSONObject responseJson) {
//        try
//        {
//            String response = responseJson.get("result").toString();
//            Toast.makeText( _activity, response, Toast.LENGTH_LONG).show();
//            Intent entranceIntent = new Intent(_activity.getBaseContext(), EntranceActivity.class);
//            _activity.startActivity(entranceIntent);
//        } catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
//
//    }
}
