package com.example.classroomclient.Domain;

public class Request{

    public RequestMeta meta;
    public Object data;

    public Request(RequestMeta meta, Object data){
        this.meta = meta;
        this.data = data;
    }
}
