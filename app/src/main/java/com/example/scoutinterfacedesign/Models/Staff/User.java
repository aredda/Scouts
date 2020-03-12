package com.example.scoutinterfacedesign.Models.Staff;

import java.io.Serializable;

public class User implements Serializable
{
    public Leader leader;
    public String username, password;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
