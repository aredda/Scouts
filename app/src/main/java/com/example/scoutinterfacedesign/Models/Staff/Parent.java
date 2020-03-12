package com.example.scoutinterfacedesign.Models.Staff;

public class Parent extends Person
{
    public String occupation;
    public String mobileNumber;

    public Parent(int id)
    {
        super(id);
    }
    public Parent(int id, String firstName, String lastName)
    {
        super(id, firstName, lastName);
    }
}
